package com.smartdubai.assesment.eShop.controller.requests;

import com.smartdubai.assesment.eShop.constants.BookType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookUpdateRequest {

    private     String     name;
    private     String     description;
    private String     author;
    private BookType   type;
    private BigDecimal price;
    private     String     ISBN;
}
