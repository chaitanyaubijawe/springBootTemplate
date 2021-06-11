package com.smartdubai.assesment.eShop.dto;

import com.smartdubai.assesment.eShop.constants.BookType;
import com.smartdubai.assesment.eShop.controller.requests.BookTypeSubset;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookDto {

  private Long id;
  private String name;
  private String description;
  private String author;

  @BookTypeSubset(anyOf = {BookType.FICTION, BookType.COMIC})
  private BookType type;

  private BigDecimal price;
  private String ISBN;
  private boolean isPromoApplicable;
}
