package io.bootify.book_shop.repos;

import io.bootify.book_shop.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartRepository extends JpaRepository<Cart, Long> {
}
