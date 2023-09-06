package com.lucaslucenak.springawssqs.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public record Address(
        @Id
        Long id,
        String addressLine1,
        String addressLine2,
        String cep,
        String city,
        String country) {
}
