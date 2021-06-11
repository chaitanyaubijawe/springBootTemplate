package com.smartdubai.assesment.eShop;

import com.smartdubai.assesment.eShop.constants.BookType;
import com.smartdubai.assesment.eShop.dto.BookDto;
import com.smartdubai.assesment.eShop.entities.Book;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Random;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class AbstractTest {

  public Book generateRandomBook(String ext) {
    Book book = new Book();
    String rndStr = ext + new Random().nextInt(9999999);
    book.setName("Name-" + rndStr);
    book.setDescription("Desc-" + rndStr);
    book.setAuthor("Author-" + rndStr);
    book.setType(BookType.COMIC.getBookType());
    book.setISBN("ISBN-" + rndStr);
    book.setPrice(new BigDecimal(new Random().nextInt(100)));
    return book;
  }

  public BookDto generateRandomBookDto (String ext, BookType type) {
    BookDto book = new BookDto();
    String rndStr = ext + new Random().nextInt(9999999);
    book.setName("Name-" + rndStr);
    book.setDescription("Desc-" + rndStr);
    book.setAuthor("Author-" + rndStr);
    book.setType(type);
    book.setISBN("ISBN-" + rndStr);
    book.setPrice(new BigDecimal(new Random().nextInt(100)));
    return book;
  }
}
