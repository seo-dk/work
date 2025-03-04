package com.eazybytes.accounts.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.eazybytes.accounts.dto.AccountsDto;
import com.eazybytes.accounts.dto.CardsDto;
import com.eazybytes.accounts.dto.MergeDto;
import com.eazybytes.accounts.entity.Accounts;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.exception.CustomCircuitBreakerException;
import com.eazybytes.accounts.exception.ResourceNotFoundException;
import com.eazybytes.accounts.mapper.AccountsMapper;
import com.eazybytes.accounts.mapper.CustomerMapper;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.dto.LoansDto;
import com.eazybytes.accounts.service.IAccountsMerge;
import com.eazybytes.accounts.service.client.CardsFeignClient;
import com.eazybytes.accounts.service.client.LoansFeignClient;
import com.eazybytes.accounts.repository.CustomerRepository;
import com.eazybytes.accounts.repository.AccountsRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountsMergeImpl implements IAccountsMerge{

    private final CustomerRepository customerRepository;
    private final AccountsRepository accountsRepository;
    private final CardsFeignClient cardsFeignClient;
    private final LoansFeignClient loansFeignClient;
    private final RabbitMqProducer rabbitMQProducer;
    private final CircuitBreakerRegistry circuitBreakerRegistry;

    @Override
    @CircuitBreaker(name = "accountServiceFallback", fallbackMethod = "fallbackService")
    public MergeDto getMergeDetails(@RequestParam String mobileNumber) {

        if (Math.random() > 0.5) {
            throw new RuntimeException("Account service error");
        }
        
        // 고객 정보 조회
        Customer customer =  customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
            () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
            () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        // 카드 정보 조회 (Feign Client 호출)
        CardsDto cardsDto = cardsFeignClient.fetchCardDetails(mobileNumber).getBody();

        // 대출 정보 조회 (Feign Client 호출)
        LoansDto loansDto = loansFeignClient.fetchLoanDetails(mobileNumber).getBody();

        // 결과 통합
        return new MergeDto(customerDto, cardsDto, loansDto);
    }

    //서킷 브레이커 Fallback 메서드
    public MergeDto fallbackService(String mobileNumber, Throwable t) {

        // 현재 서킷 브레이커 상태 확인
        io.github.resilience4j.circuitbreaker.CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("accountServiceFallback");
        io.github.resilience4j.circuitbreaker. CircuitBreaker.State state = circuitBreaker.getState();

        String errorMessage = String.format(" [Circuit Breaker open] 고객(%s)의 카드 또는 대출 정보를 가져오지 못했습니다. 원인: %s",
        mobileNumber, t.getMessage());
        rabbitMQProducer.sendMessage(errorMessage);

        if (state==io.github.resilience4j.circuitbreaker.CircuitBreaker.State.OPEN) {
            throw new CustomCircuitBreakerException("서킷 브레이커가 OPEN 상태입니다. 요청을 처리할 수 없습니다.", t);
        }
        throw new RuntimeException("Account service error");
            
        // CustomerDto customerDto = new CustomerDto();
        // customerDto.setName("서비스 이용 불가");
        // customerDto.setMobileNumber(mobileNumber);

        // return new MergeDto(customerDto, null, null); 
    }
}
