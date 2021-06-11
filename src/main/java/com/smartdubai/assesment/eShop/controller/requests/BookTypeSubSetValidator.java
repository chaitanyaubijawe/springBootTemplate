package com.smartdubai.assesment.eShop.controller.requests;

import com.smartdubai.assesment.eShop.constants.BookType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class BookTypeSubSetValidator implements ConstraintValidator<BookTypeSubset, BookType> {
  private BookType[] subset;

  @Override
  public void initialize(BookTypeSubset constraint) {
    this.subset = constraint.anyOf();
  }

  @Override
  public boolean isValid(BookType value, ConstraintValidatorContext context) {
    return value == null || Arrays.asList(subset).contains(value);
  }
}
