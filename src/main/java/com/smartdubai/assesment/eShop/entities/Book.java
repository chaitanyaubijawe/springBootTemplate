package com.smartdubai.assesment.eShop.entities;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String     name;
    private String     description;
    private String     author;
    private String     type;
    private BigDecimal price;
    @Column(name = "ISBN")
    private String ISBN;

}
