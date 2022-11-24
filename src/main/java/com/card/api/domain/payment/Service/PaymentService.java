package com.card.api.domain.payment.Service;

import com.card.api.domain.exception.ServiceError;
import com.card.api.domain.exception.ServiceException;
import com.card.api.domain.payment.Repository.PaymentRepository;
import com.card.api.domain.payment.Repository.UserPaymentRepository;
import com.card.api.domain.payment.dto.PaymentDto;
import com.card.api.domain.payment.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final RedissonClient redissonClient;
    private final UserPaymentRepository userPaymentRepository;
    private final PaymentRepository paymentRepository;

    public PaymentDto registPay(PaymentDto requestDto) throws ServiceException {
        RLock lock = redissonClient.getLock(requestDto.getId().toString());
        try{
            boolean available = lock.tryLock(10,1, TimeUnit.SECONDS);
            if(!available){
                System.out.println("lock 획득 실패");
            }
            requestDto = this.Pay(requestDto);


        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

        return requestDto;
    }

    @Transactional
    public PaymentDto Pay(PaymentDto requestDto){
        paymentRepository.findById(requestDto.getId())
                .ifPresent(o -> {
                    throw new ServiceException(ServiceError.CONFLICT_DATA, "해당 데이터가 이미 등록되어 있습니다.");
                });

        Payment dto = paymentRepository.findById(requestDto.getId()).orElseThrow();
        dto.cardUsages(requestDto.getAmount());
        return PaymentDto.from(paymentRepository.save(requestDto.toEntity()));
    }
}
