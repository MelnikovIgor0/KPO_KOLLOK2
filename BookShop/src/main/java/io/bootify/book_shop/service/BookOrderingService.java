package io.bootify.book_shop.service;

import io.bootify.book_shop.domain.Book;
import io.bootify.book_shop.domain.BookOrdering;
import io.bootify.book_shop.domain.Cart;
import io.bootify.book_shop.model.BookOrderingDTO;
import io.bootify.book_shop.repos.BookOrderingRepository;
import io.bootify.book_shop.repos.BookRepository;
import io.bootify.book_shop.repos.CartRepository;
import io.bootify.book_shop.util.NotFoundException;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class BookOrderingService {

    private final BookOrderingRepository bookOrderingRepository;
    private final CartRepository cartRepository;
    private final BookRepository bookRepository;

    public BookOrderingService(final BookOrderingRepository bookOrderingRepository,
                               final CartRepository cartRepository, final BookRepository bookRepository) {
        this.bookOrderingRepository = bookOrderingRepository;
        this.cartRepository = cartRepository;
        this.bookRepository = bookRepository;
    }

    public List<BookOrderingDTO> findAll() {
        final List<BookOrdering> bookOrderings = bookOrderingRepository.findAll(Sort.by("id"));
        return bookOrderings.stream()
                .map(bookOrdering -> mapToDTO(bookOrdering, new BookOrderingDTO()))
                .toList();
    }

    public List<BookOrderingDTO> findByOrderId(long orderId) {
        final List<BookOrdering> bookOrderings = bookOrderingRepository.findAll(Sort.by("id"));
        List<BookOrdering> goodBooks = new ArrayList<>();
        for (BookOrdering book: bookOrderings) {
            if (book.getCartId() == orderId) {
                goodBooks.add(book);
            }
        }
        return goodBooks.stream()
                .map(bookOrdering -> mapToDTO(bookOrdering, new BookOrderingDTO()))
                .toList();
    }

    public BookOrderingDTO get(final Long id) {
        return bookOrderingRepository.findById(id)
                .map(bookOrdering -> mapToDTO(bookOrdering, new BookOrderingDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final BookOrderingDTO bookOrderingDTO) {
        final BookOrdering bookOrdering = new BookOrdering();
        mapToEntity(bookOrderingDTO, bookOrdering);
        return bookOrderingRepository.save(bookOrdering).getId();
    }

    public void update(final Long id, final BookOrderingDTO bookOrderingDTO) {
        final BookOrdering bookOrdering = bookOrderingRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(bookOrderingDTO, bookOrdering);
        bookOrderingRepository.save(bookOrdering);
    }

    public void delete(final Long id) {
        bookOrderingRepository.deleteById(id);
    }

    private BookOrderingDTO mapToDTO(final BookOrdering bookOrdering,
                                     final BookOrderingDTO bookOrderingDTO) {
        bookOrderingDTO.setId(bookOrdering.getId());
        bookOrderingDTO.setBookId(bookOrdering.getBookId());
        bookOrderingDTO.setNumberBooks(bookOrdering.getNumberBooks());
        bookOrderingDTO.setCartId(bookOrdering.getCartId());
        bookOrderingDTO.setOrderId(bookOrdering.getOrderId() == null ? null : bookOrdering.getOrderId().getId());
        bookOrderingDTO.setBookUUID(bookOrdering.getBookUUID() == null ? null : bookOrdering.getBookUUID().getId());
        return bookOrderingDTO;
    }

    private BookOrdering mapToEntity(final BookOrderingDTO bookOrderingDTO,
                                     final BookOrdering bookOrdering) {
        bookOrdering.setBookId(bookOrderingDTO.getBookId());
        bookOrdering.setNumberBooks(bookOrderingDTO.getNumberBooks());
        bookOrdering.setCartId(bookOrderingDTO.getCartId());
        final Cart orderId = bookOrderingDTO.getOrderId() == null ? null : cartRepository.findById(bookOrderingDTO.getOrderId())
                .orElseThrow(() -> new NotFoundException("orderId not found"));
        bookOrdering.setOrderId(orderId);
        final Book bookUUID = bookOrderingDTO.getBookUUID() == null ? null : bookRepository.findById(bookOrderingDTO.getBookUUID())
                .orElseThrow(() -> new NotFoundException("bookUUID not found"));
        bookOrdering.setBookUUID(bookUUID);
        return bookOrdering;
    }

}
