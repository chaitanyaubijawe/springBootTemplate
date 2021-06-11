package com.smartdubai.assesment.eShop.services;

import com.smartdubai.assesment.eShop.AbstractTest;
import com.smartdubai.assesment.eShop.constants.BookType;
import com.smartdubai.assesment.eShop.constants.PromoDetails;
import com.smartdubai.assesment.eShop.controller.requests.BookSearchParam;
import com.smartdubai.assesment.eShop.controller.response.CheckoutDetails;
import com.smartdubai.assesment.eShop.entities.Book;
import liquibase.pro.packaged.D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Arrays;

@Transactional
class BooksServiceImplTest extends AbstractTest {

  @Autowired BookService bookService;

  @Test
  public void testSaveBook() {

    Book book = generateRandomBook("save-");
    final Book save = bookService.addBook(book);
    final Book byId = bookService.getBookById(save.getId());
    Assertions.assertNotNull(byId);
    Assertions.assertNotNull(byId.getName());
    Assertions.assertNotNull(byId.getAuthor());
    Assertions.assertNotNull(byId.getDescription());
    Assertions.assertNotNull(byId.getPrice());
    Assertions.assertNotNull(byId.getISBN());
  }

  @Test
  public void testSearchBookByName() {

    Book book = generateRandomBook("search-");
    final Book save = bookService.addBook(book);
    BookSearchParam param = new BookSearchParam();
    param.setName(save.getName());
    final Page<Book> books = bookService.searchBooks(param, Pageable.ofSize(10));
    books
        .getContent()
        .forEach(
            bookItem -> {
              Assertions.assertNotNull(bookItem);
              Assertions.assertNotNull(bookItem.getName());
              Assertions.assertEquals(bookItem.getName(), save.getName());
              Assertions.assertNotNull(bookItem.getAuthor());
              Assertions.assertNotNull(bookItem.getDescription());
              Assertions.assertNotNull(bookItem.getPrice());
              Assertions.assertNotNull(bookItem.getISBN());
            });
  }

  @Test
  public void testSearchBookByAuthor() {

    Book book = generateRandomBook("search-");
    final Book save = bookService.addBook(book);
    BookSearchParam param = new BookSearchParam();
    param.setAuthor(save.getAuthor());
    final Page<Book> books = bookService.searchBooks(param, Pageable.ofSize(10));
    books
        .getContent()
        .forEach(
            bookItem -> {
              Assertions.assertNotNull(bookItem);
              Assertions.assertNotNull(bookItem.getName());
              Assertions.assertEquals(bookItem.getName(), save.getName());
              Assertions.assertNotNull(bookItem.getAuthor());
              Assertions.assertNotNull(bookItem.getDescription());
              Assertions.assertNotNull(bookItem.getPrice());
              Assertions.assertNotNull(bookItem.getISBN());
            });
  }

  @Test
  public void testSearchBookByType() {

    Book book = generateRandomBook("search-");
    final Book save = bookService.addBook(book);
    BookSearchParam param = new BookSearchParam();
    param.setType(save.getType());
    final Page<Book> books = bookService.searchBooks(param, Pageable.ofSize(10));
    books
        .getContent()
        .forEach(
            bookItem -> {
              Assertions.assertNotNull(bookItem);
              Assertions.assertNotNull(bookItem.getName());
              Assertions.assertEquals(bookItem.getType(), save.getType());
              Assertions.assertNotNull(bookItem.getAuthor());
              Assertions.assertNotNull(bookItem.getDescription());
              Assertions.assertNotNull(bookItem.getPrice());
              Assertions.assertNotNull(bookItem.getISBN());
            });
  }

  @Test
  public void testUpdateBook() {

    Book book = generateRandomBook("save-");
    final Book save = bookService.addBook(book);
    Book updatedBook = generateRandomBook("update-");
    final Book bookUpdatedDb = bookService.updateBook(save.getId(), updatedBook);

    Assertions.assertNotNull(bookUpdatedDb);
    Assertions.assertNotNull(bookUpdatedDb.getName());
    Assertions.assertEquals(bookUpdatedDb.getName(), save.getName());
    Assertions.assertNotNull(bookUpdatedDb.getAuthor());
    Assertions.assertEquals(bookUpdatedDb.getAuthor(), save.getAuthor());
    Assertions.assertNotNull(bookUpdatedDb.getDescription());
    Assertions.assertEquals(bookUpdatedDb.getDescription(), save.getDescription());
    Assertions.assertNotNull(bookUpdatedDb.getPrice());
    Assertions.assertEquals(bookUpdatedDb.getPrice(), save.getPrice());
    Assertions.assertNotNull(bookUpdatedDb.getISBN());
    Assertions.assertEquals(bookUpdatedDb.getISBN(), save.getISBN());
  }

  @Test
  public void testDeleteBook() {

    Book book = generateRandomBook("save-");
    final Book save = bookService.addBook(book);

    bookService.deleteBook(save.getId());

    Assertions.assertThrows(
        JpaObjectRetrievalFailureException.class,
        () -> {
          bookService.getBookById(save.getId());
        });
  }

  @Test
  public void testCheckoutBooks() {

    Book book1 = generateRandomBook("checkout1-");
    Book book2 = generateRandomBook("checkout2-");
    book2.setType(BookType.FICTION.getBookType());
    bookService.addBook(book1);
    bookService.addBook(book2);
      final CheckoutDetails checkout = bookService.checkout(Arrays.asList(book1, book2), PromoDetails.TEN);
      BigDecimal dsc = book2.getPrice().multiply(PromoDetails.TEN.getDiscount()).divide(new BigDecimal(100));
      BigDecimal oAmnt = book1.getPrice().add(book2.getPrice().subtract(dsc));

      Assertions.assertEquals(checkout.getOrderAmount(), oAmnt);
      Assertions.assertEquals(checkout.getTotalDiscount(), dsc);
  }
}
