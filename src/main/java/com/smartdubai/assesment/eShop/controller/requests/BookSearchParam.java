package com.smartdubai.assesment.eShop.controller.requests;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookSearchParam {

    private String     name;
    private String     author;
    private String     type;
    private BigDecimal price;
}
