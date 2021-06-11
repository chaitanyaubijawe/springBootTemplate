package com.smartdubai.assesment.eShop.controller;

import com.smartdubai.assesment.eShop.AbstractControllerTest;
import com.smartdubai.assesment.eShop.constants.BookType;
import com.smartdubai.assesment.eShop.constants.PromoDetails;
import com.smartdubai.assesment.eShop.controller.response.CheckoutDetails;
import com.smartdubai.assesment.eShop.dto.BookDto;
import com.smartdubai.assesment.eShop.entities.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.transaction.Transactional;
import java.util.Arrays;

@Transactional
class BooksControllerTest extends AbstractControllerTest {

  @Test
  public void testAddBook() throws Exception {
    Book book = generateRandomBook("cnt-Book-");
    BookDto byId = this.post(book, BookDto.class);
    Assertions.assertNotNull(byId);
    Assertions.assertNotNull(byId.getName());
    Assertions.assertNotNull(byId.getAuthor());
    Assertions.assertNotNull(byId.getDescription());
    Assertions.assertNotNull(byId.getPrice());
    Assertions.assertNotNull(byId.getISBN());
  }

  @Test
  public void testUpdateBook() throws Exception {
    Book book = generateRandomBook("cnt-Book-");
    Book bookToUpdate = generateRandomBook("cnt-Book-");
    BookDto save = this.post(book, BookDto.class);
    BookDto bookUpdatedDb = this.put(bookToUpdate, BookDto.class, "/" + save.getId());
    BookDto updatedData = this.get(BookDto.class, "/"+save.getId());
    Assertions.assertNotNull(bookUpdatedDb);
    Assertions.assertNotNull(bookUpdatedDb.getName());
    Assertions.assertEquals(bookUpdatedDb.getName(), updatedData.getName());
    Assertions.assertNotNull(bookUpdatedDb.getAuthor());
    Assertions.assertEquals(bookUpdatedDb.getAuthor(), updatedData.getAuthor());
    Assertions.assertNotNull(bookUpdatedDb.getDescription());
    Assertions.assertEquals(bookUpdatedDb.getDescription(), updatedData.getDescription());
    Assertions.assertNotNull(bookUpdatedDb.getPrice());
    Assertions.assertEquals(bookUpdatedDb.getPrice(), updatedData.getPrice());
    Assertions.assertNotNull(bookUpdatedDb.getISBN());
    Assertions.assertEquals(bookUpdatedDb.getISBN(), updatedData.getISBN());
  }

  @Test
  public void testDeleteBook() throws Exception {
    Book book = generateRandomBook("cnt-Book-");
    BookDto save = this.post(book, BookDto.class);
    boolean deleted = this.delete(Boolean.class, "/" + save.getId());
    Assertions.assertTrue(deleted);
  }

  @Test
  public void testCheckoutBook() throws Exception {
    BookDto bookFiction = generateRandomBookDto("cnt-Book-", BookType.FICTION);
    BookDto bookComic = generateRandomBookDto("cnt-Book-", BookType.COMIC);
    BookDto saveFiction = this.post(bookFiction, BookDto.class);
    BookDto saveComic = this.post(bookComic, BookDto.class);

    CheckoutDetails checkoutDetails = this.post(Arrays.asList(saveComic, saveFiction),CheckoutDetails.class, "/checkout?promoCode=" +
                                                                                                             PromoDetails.TEN.getPromoCode());
    Assertions.assertNotNull(checkoutDetails);
    Assertions.assertNotNull(checkoutDetails.getOrderAmount());
    Assertions.assertNotNull(checkoutDetails.getTotalDiscount());

  }

// Helper methods
  @Override
  protected String url() {

    return "/api/v1/book";
  }
}
