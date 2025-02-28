package com.eazybytes.accounts.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.eazybytes.accounts.dto.AccountsDto;
import com.eazybytes.accounts.dto.CardsDto;
import com.eazybytes.accounts.dto.MergeDto;
import com.eazybytes.accounts.entity.Accounts;
import com.eazybytes.accounts.entity.Customer;
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
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountsMergeImpl implements IAccountsMerge{

    private final CustomerRepository customerRepository;
    private final AccountsRepository accountsRepository;
    private final CardsFeignClient cardsFeignClient;
    private final LoansFeignClient loansFeignClient;

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
    public Map<String, Object> fallbackService(String mobileNumber, Throwable t) {
        
        Map<String, Object> fallbackResponse = new HashMap<>();
    
        // 고객 정보 Fallback
        Map<String, Object> customerFallback = new HashMap<>();
        customerFallback.put("customerId", null);
        customerFallback.put("name", "서비스 이용 불가");
        customerFallback.put("mobileNumber", mobileNumber);
        
        // 카드 정보 Fallback
        Map<String, Object> cardFallback = new HashMap<>();
        cardFallback.put("cardId", null);
        cardFallback.put("cardNumber", "카드 정보 조회 실패");
        
        // 대출 정보 Fallback
        Map<String, Object> loanFallback = new HashMap<>();
        loanFallback.put("loanId", null);
        loanFallback.put("loanType", "대출 정보 조회 실패");

        // 전체 응답 조합
        fallbackResponse.put("customer", customerFallback);
        fallbackResponse.put("cards", cardFallback);
        fallbackResponse.put("loans", loanFallback);

        return fallbackResponse;
    }

}
