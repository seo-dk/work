package com.eazybytes.accounts.service;
import org.springframework.web.bind.annotation.RequestParam;
import com.eazybytes.accounts.dto.MergeDto;

public interface IAccountsMerge {
    public MergeDto getMergeDetails(@RequestParam String mobileNumber);   
}
