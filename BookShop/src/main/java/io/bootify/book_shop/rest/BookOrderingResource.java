package io.bootify.book_shop.rest;

import io.bootify.book_shop.model.BookOrderingDTO;
import io.bootify.book_shop.service.BookOrderingService;
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
@RequestMapping(value = "/api/cart", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookOrderingResource {

    private final BookOrderingService bookOrderingService;

    public BookOrderingResource(final BookOrderingService bookOrderingService) {
        this.bookOrderingService = bookOrderingService;
    }

    @GetMapping("find-by-order/{id}")
    @Tag(name = "метод возвращает все заказы по айдишнику корзины")
    public ResponseEntity<List<BookOrderingDTO>> getBooksByCartId(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(bookOrderingService.findByOrderId(id));
    }

    @GetMapping
    @Tag(name = "метод возвращает список из всех заказов (книга - кол-во книг)")
    public ResponseEntity<List<BookOrderingDTO>> getAllBookOrderings() {
        return ResponseEntity.ok(bookOrderingService.findAll());
    }

    @GetMapping("/{id}")
    @Tag(name = "метод возвращает заказ по айдишнику")
    public ResponseEntity<BookOrderingDTO> getBookOrdering(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(bookOrderingService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    @Tag(name = "метод создает книгу")
    public ResponseEntity<Long> createBookOrdering(
            @RequestBody @Valid final BookOrderingDTO bookOrderingDTO) {
        final Long createdId = bookOrderingService.create(bookOrderingDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Tag(name = "метод обновляет книгу по айдишнику")
    public ResponseEntity<Void> updateBookOrdering(@PathVariable(name = "id") final Long id,
                                                   @RequestBody @Valid final BookOrderingDTO bookOrderingDTO) {
        bookOrderingService.update(id, bookOrderingDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    @Tag(name = "метод удаляет книгу по айдишнику")
    public ResponseEntity<Void> deleteBookOrdering(@PathVariable(name = "id") final Long id) {
        bookOrderingService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
