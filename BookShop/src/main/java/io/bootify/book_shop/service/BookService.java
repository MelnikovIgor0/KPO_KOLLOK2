package io.bootify.book_shop.service;

import io.bootify.book_shop.domain.Book;
import io.bootify.book_shop.model.BookDTO;
import io.bootify.book_shop.repos.BookRepository;
import io.bootify.book_shop.util.NotFoundException;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookDTO> findAll() {
        final List<Book> books = bookRepository.findAll(Sort.by("id"));
        return books.stream()
                .map(book -> mapToDTO(book, new BookDTO()))
                .toList();
    }

    public BookDTO get(final Long id) {
        return bookRepository.findById(id)
                .map(book -> mapToDTO(book, new BookDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final BookDTO bookDTO) {
        final Book book = new Book();
        mapToEntity(bookDTO, book);
        return bookRepository.save(book).getId();
    }

    public void update(final Long id, final BookDTO bookDTO) {
        final Book book = bookRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(bookDTO, book);
        bookRepository.save(book);
    }

    public void delete(final Long id) {
        bookRepository.deleteById(id);
    }

    private BookDTO mapToDTO(final Book book, final BookDTO bookDTO) {
        bookDTO.setId(book.getId());
        bookDTO.setName(book.getName());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setGenre(book.getGenre());
        bookDTO.setPrice(book.getPrice());
        return bookDTO;
    }

    private Book mapToEntity(final BookDTO bookDTO, final Book book) {
        book.setName(bookDTO.getName());
        book.setAuthor(bookDTO.getAuthor());
        book.setGenre(bookDTO.getGenre());
        book.setPrice(bookDTO.getPrice());
        return book;
    }

    public boolean nameExists(final String name) {
        return bookRepository.existsByNameIgnoreCase(name);
    }

}
