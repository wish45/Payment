package com.card.api.domain.payment.Repository;

import com.card.api.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPaymentRepository extends JpaRepository<Payment, Long> {

}
