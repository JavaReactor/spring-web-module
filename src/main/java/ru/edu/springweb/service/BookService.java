package ru.edu.springweb.service;

import org.springframework.stereotype.Service;
import ru.edu.springweb.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookService {

    private final List<Book> books = new ArrayList<>();
    private Long idCounter = 0L;

    public List<Book> getAllBooks() {
        return books;
    }

    public Optional<Book> getBookById(int id) {
        return books.stream().filter(book -> book.getId() == id).findFirst();
    }

    public Book addBook(Book book) {
        book.setId(idCounter++);
        books.add(book);
        return book;
    }

    public boolean updateBook(int id, Book updatedBook) {
        return getBookById(id).map(existingBook -> {
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            return true;
        }).orElse(false);
    }

    public boolean deleteBook(Long id) {
        return books.removeIf(book -> Objects.equals(book.getId(), id));
    }
}
