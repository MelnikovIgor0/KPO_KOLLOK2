package io.bootify.book_shop.repos;

import io.bootify.book_shop.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByNameIgnoreCase(String name);

}
