package io.bootify.book_shop.rest;

import io.bootify.book_shop.model.BookDTO;
import io.bootify.book_shop.service.BookService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/books", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookResource {

    private final BookService bookService;

    public BookResource(final BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    @Tag(name = "метод возвращает все книги")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping("/{id}")
    @Tag(name = "метод возвращает книгу по айдишнику")
    public ResponseEntity<BookDTO> getBook(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(bookService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    @Tag(name = "метод создает книгу по указанным входным данным")
    public ResponseEntity<Long> createBook(@RequestBody @Valid final BookDTO bookDTO) {
        final Long createdId = bookService.create(bookDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Tag(name = "метод обновляет информацию о книге")
    public ResponseEntity<Void> updateBook(@PathVariable(name = "id") final Long id,
                                           @RequestBody @Valid final BookDTO bookDTO) {
        bookService.update(id, bookDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    @Tag(name = "метод удаляет книгу")
    public ResponseEntity<Void> deleteBook(@PathVariable(name = "id") final Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
