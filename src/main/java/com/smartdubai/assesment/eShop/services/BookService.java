package com.smartdubai.assesment.eShop.services;

import com.smartdubai.assesment.eShop.constants.PromoDetails;
import com.smartdubai.assesment.eShop.controller.requests.BookSearchParam;
import com.smartdubai.assesment.eShop.controller.response.CheckoutDetails;
import com.smartdubai.assesment.eShop.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
  Book getBookById(final Long id);

  Page<Book> searchBooks (BookSearchParam search, Pageable pageables);

  Book addBook (Book toModel);

  Book updateBook (Long id, Book toModel);
  boolean deleteBook (Long id);
  CheckoutDetails checkout (List<Book> books, PromoDetails promotionCode);
}
