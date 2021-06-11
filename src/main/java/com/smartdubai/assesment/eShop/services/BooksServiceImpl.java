package com.smartdubai.assesment.eShop.services;

import com.smartdubai.assesment.eShop.constants.BookType;
import com.smartdubai.assesment.eShop.constants.PromoDetails;
import com.smartdubai.assesment.eShop.controller.requests.BookSearchParam;
import com.smartdubai.assesment.eShop.controller.response.CheckoutDetails;
import com.smartdubai.assesment.eShop.dto.BookDto;
import com.smartdubai.assesment.eShop.entities.Book;
import com.smartdubai.assesment.eShop.repositories.BooksRepository;
import com.smartdubai.assesment.eShop.repositories.specs.BookSpecification;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class BooksServiceImpl implements BookService {

  private final BooksRepository repository;
  private final ModelMapper modelMapper;
  Logger log = LoggerFactory.getLogger(BooksServiceImpl.class);

  @Autowired
  BooksServiceImpl(final BooksRepository repository, final ModelMapper modelMapper) {
    this.repository = repository;
    this.modelMapper = modelMapper;
  }

  public Book getBookById(final Long id) {
    log.info("Retrieving book by id={}", id);
    return repository.getById(id);
  }

  @Override
  public Page<Book> searchBooks(final BookSearchParam search, Pageable pageable) {
    log.info("Searching book by search={}", search);
    BookSpecification specification =
        new BookSpecification(
            search.getName(), search.getAuthor(), search.getType(), search.getPrice());
    return this.repository.findAll(specification, pageable);
  }

  @Override
  public Book addBook(final Book book) {
    log.info("Saving book={}", book);
    return this.repository.save(book);
  }

  @Override
  public Book updateBook(final Long id, final Book toModel) {
    log.info("Updating book={}", toModel);
    toModel.setId(id);
    return this.repository.save(toModel);
  }

  @Override
  public boolean deleteBook(final Long id) {
    log.info("deleting book by id={}", id);
    this.repository.deleteById(id);
    return true;
  }

  @Override
  public CheckoutDetails checkout(final List<Book> books, final PromoDetails promotionCode) {
    log.info("Checkout API for promo code={}", promotionCode);
    List<BookDto> bookDtoList = new ArrayList<>();
    BigDecimal orderAmount = new BigDecimal(0);
    BigDecimal totalDiscount = new BigDecimal(0);
    for (Book item : books) {
      BookDto dto = toDto(item);
      BigDecimal dsc = item.getPrice();
      if (promotionCode.equals(PromoDetails.TEN)
          && item.getType().equals(BookType.FICTION.getBookType())) {
        dto.setPromoApplicable(true);
        dsc = item.getPrice().multiply(PromoDetails.TEN.getDiscount().divide(new BigDecimal(100)));
        totalDiscount = totalDiscount.add(dsc);
        orderAmount = orderAmount.add(item.getPrice().subtract(dsc));
      } else {

        orderAmount = orderAmount.add(item.getPrice());
      }
      bookDtoList.add(dto);
    }
    log.info("Checkout API response for promo={} orderAmount={}, discount={}",promotionCode, orderAmount, totalDiscount);
    return CheckoutDetails.builder()
        .orderAmount(orderAmount)
        .totalDiscount(totalDiscount)
        .books(bookDtoList)
        .build();
  }

  // Helper methods
  private BookDto toDto(Book entity) {

    return modelMapper.map(entity, BookDto.class);
  }
}
