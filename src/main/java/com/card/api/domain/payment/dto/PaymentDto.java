package com.card.api.domain.payment.dto;

import com.card.api.domain.payment.entity.Category;
import com.card.api.domain.payment.entity.Payment;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.card.api.domain.utils.ValidGroups;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentDto {

    @NotNull(groups = ValidGroups.updateValidGroup.class, message = "id")
    private Long id;

    @NotEmpty(groups = ValidGroups.updateValidGroup.class, message = "created_at")
    private String created_at;

    @NotEmpty(groups = ValidGroups.updateValidGroup.class, message = "category")
    private Category category;

    @NotEmpty(groups = ValidGroups.updateValidGroup.class, message = "partner")
    private String partner;

    @NotNull(groups = ValidGroups.updateValidGroup.class, message = "amount")
    private Long amount;


    public static PaymentDto from(Payment payment) {
        return PaymentDto.builder()
                .id(payment.getId())
                .created_at(payment.getCreated_at())
                .category(payment.getCategory())
                .partner(payment.getPartner())
                .build();
    }

    public static List<PaymentDto> from(List<Payment> payments) {
        return payments.stream()
                .map(PaymentDto::from)
                .collect(Collectors.toList());
    }


    public Payment toEntity() {
        return Payment.builder()
                .id(id)
                .created_at(created_at)
                .category(category)
                .partner(partner)
                .amount(amount)
                .build();
    }
}
