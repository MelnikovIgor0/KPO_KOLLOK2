package io.bootify.book_shop.rest;

import io.bootify.book_shop.model.CartDTO;
import io.bootify.book_shop.service.CartService;
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
@RequestMapping(value = "/api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class CartResource {

    private final CartService cartService;

    public CartResource(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    @Tag(name = "метод созвращает все корзины")
    public ResponseEntity<List<CartDTO>> getAllCarts() {
        return ResponseEntity.ok(cartService.findAll());
    }

    @GetMapping("/{id}")
    @Tag(name = "метод возвращает корзину по айдишнику")
    public ResponseEntity<CartDTO> getCart(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(cartService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    @Tag(name = "метод создает корзину")
    public ResponseEntity<Long> createCart(@RequestBody @Valid final CartDTO cartDTO) {
        final Long createdId = cartService.create(cartDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Tag(name = "метод обновляет корзину")
    public ResponseEntity<Void> updateCart(@PathVariable(name = "id") final Long id,
                                           @RequestBody @Valid final CartDTO cartDTO) {
        cartService.update(id, cartDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    @Tag(name = "метод удаляет корзину")
    public ResponseEntity<Void> deleteCart(@PathVariable(name = "id") final Long id) {
        cartService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
