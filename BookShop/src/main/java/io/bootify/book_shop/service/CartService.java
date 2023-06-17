package io.bootify.book_shop.service;

import io.bootify.book_shop.domain.Cart;
import io.bootify.book_shop.model.CartDTO;
import io.bootify.book_shop.repos.CartRepository;
import io.bootify.book_shop.util.NotFoundException;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(final CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<CartDTO> findAll() {
        final List<Cart> carts = cartRepository.findAll(Sort.by("id"));
        return carts.stream()
                .map(cart -> mapToDTO(cart, new CartDTO()))
                .toList();
    }

    public CartDTO get(final Long id) {
        return cartRepository.findById(id)
                .map(cart -> mapToDTO(cart, new CartDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CartDTO cartDTO) {
        final Cart cart = new Cart();
        mapToEntity(cartDTO, cart);
        return cartRepository.save(cart).getId();
    }

    public void update(final Long id, final CartDTO cartDTO) {
        final Cart cart = cartRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(cartDTO, cart);
        cartRepository.save(cart);
    }

    public void delete(final Long id) {
        cartRepository.deleteById(id);
    }

    private CartDTO mapToDTO(final Cart cart, final CartDTO cartDTO) {
        cartDTO.setId(cart.getId());
        return cartDTO;
    }

    private Cart mapToEntity(final CartDTO cartDTO, final Cart cart) {
        return cart;
    }

}
