package org.erhanmutlu.asyncpaymentgateway.application.controller.contract.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PayRequest {

    private String referenceId;
    @JsonIgnore
    private String phase;
    private BigDecimal amount;
    private String currency = "TRY";
    private Integer installment;
    private String cardUserKey;
    private String cardKey;
}
