package io.bootify.book_shop.repos;

import io.bootify.book_shop.domain.BookOrdering;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookOrderingRepository extends JpaRepository<BookOrdering, Long> {
}
