package org.erhanmutlu.asyncpaymentgateway.application.controller.contract.request.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Card {
    private String holderName;
    private String number;
    private int expireYear;
    private int expireMonth;
    private String cvv;
}
