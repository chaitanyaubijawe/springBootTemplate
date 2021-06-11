package com.smartdubai.assesment.eShop.controller;

import com.smartdubai.assesment.eShop.constants.PromoDetails;
import com.smartdubai.assesment.eShop.controller.requests.BookSearchParam;
import com.smartdubai.assesment.eShop.controller.requests.BookUpdateRequest;
import com.smartdubai.assesment.eShop.controller.response.CheckoutDetails;
import com.smartdubai.assesment.eShop.dto.BookDto;
import com.smartdubai.assesment.eShop.entities.Book;
import com.smartdubai.assesment.eShop.services.BooksServiceImpl;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/book")
public class BooksController {

  private final BooksServiceImpl service;
  private final ModelMapper modelMapper;

  @Autowired
  BooksController(final BooksServiceImpl service, final ModelMapper modelMapper) {

    this.service = service;
    this.modelMapper = modelMapper;
  }

  @GetMapping("/{id}")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Get book by id")})
  public BookDto getBookById(@PathVariable("id") Long id) {

    return toDto(this.service.getBookById(id));
  }

  @DeleteMapping("/{id}")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "delete a book by id")})
  public boolean deleteBookById(@PathVariable("id") Long id) {

    return this.service.deleteBook(id);
  }

  @GetMapping("/search")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Search books")})
  public Page<Book> getBookById(BookSearchParam searchParam, Pageable pageable) {
    // TODO: The list object should be of BookDto type and not Book type.
    final Page<Book> books = this.service.searchBooks(searchParam, pageable);
    return books;
  }

  @PostMapping("/checkout")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Checkout books to know offer and discount")})
  public CheckoutDetails checkoutBooks(
      @RequestParam("promoCode") PromoDetails promoCode, @RequestBody List<BookDto> bookDtoList) {

    return this.service.checkout(toModel(bookDtoList), promoCode);
  }

  @PostMapping()
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Add book")})
  public BookDto addBook(@RequestBody BookDto bookDto) {

    return toDto(service.addBook(toModel(bookDto)));
  }

  @PutMapping("/{id}")
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Update book")})
  public BookDto updateBook(@PathVariable("id") Long id, @RequestBody BookUpdateRequest bookDto) {

    return toDto(service.updateBook(id, toModel(bookDto)));
  }

  // Helper methods
  // Helper methods
  // Helper methods
  private BookDto toDto(Book entity) {

    return modelMapper.map(entity, BookDto.class);
  }

  private Book toModel(BookDto dto) {

    return modelMapper.map(dto, Book.class);
  }

  private List<Book> toModel(List<BookDto> dtoList) {

    return dtoList.stream()
        .map(dto -> modelMapper.map(dto, Book.class))
        .collect(Collectors.toList());
  }

  private Book toModel(BookUpdateRequest dto) {

    return modelMapper.map(dto, Book.class);
  }
}
