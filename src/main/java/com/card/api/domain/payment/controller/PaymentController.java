package com.card.api.domain.payment.controller;

import com.card.api.domain.payment.Service.PaymentService;
import com.card.api.domain.payment.dto.PaymentDto;
import com.card.api.domain.utils.ApiResponse;
import com.card.api.domain.utils.ValidGroups;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<ApiResponse.ApiResult<PaymentDto>> regist(@RequestBody @Validated(
            ValidGroups.updateValidGroup.class) PaymentDto paymentDto) {
        return new ResponseEntity<>(ApiResponse.success(paymentService.registPay(paymentDto)), HttpStatus.OK);
    }
}
