package com.card.api.domain.payment.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Entity
@Table(name = "tb_payment")
public class Payment implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String created_at;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String partner;

    private Long amount;


    public void cardUsages(Long amount){
        System.out.println("cardUsages : "+amount);
        if(this.amount - amount<0){
            throw new IllegalStateException("사용가능한 금액을 초과하였습니다. ");
        }
        this.amount = this.amount - amount;
    }


    @Override
    public boolean isNew() {
        return false;
    }
}
