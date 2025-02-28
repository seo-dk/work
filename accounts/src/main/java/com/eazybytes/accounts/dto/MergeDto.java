package com.eazybytes.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MergeDto {

        private CustomerDto customer;
        private CardsDto cards;
        private LoansDto loans;
}
