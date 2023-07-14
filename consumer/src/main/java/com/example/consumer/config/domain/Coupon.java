package com.example.consumer.config.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Coupon {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userid;

    public Coupon(){

    }

    public Coupon(Long userid) {
        this.userid = userid;
    }

    public Long getUserid() {
        return userid;
    }
}
