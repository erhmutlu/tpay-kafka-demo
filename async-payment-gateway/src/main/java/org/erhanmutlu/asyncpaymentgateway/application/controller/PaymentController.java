package org.erhanmutlu.asyncpaymentgateway.application.controller;

import org.erhanmutlu.asyncpaymentgateway.application.controller.contract.request.PayRequest;
import org.erhanmutlu.asyncpaymentgateway.application.controller.contract.response.PayResponse;
import org.erhanmutlu.asyncpaymentgateway.domain.PaymentPhase;
import org.erhanmutlu.asyncpaymentgateway.domain.service.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/auth")
    public PayResponse auth(@RequestBody PayRequest payRequest) {
        payRequest.setPhase(PaymentPhase.AUTH);
        String uniqueId = paymentService.pay(payRequest);
        return new PayResponse(uniqueId);
    }
}
