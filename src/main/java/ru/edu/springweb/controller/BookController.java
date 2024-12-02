package ru.edu.springweb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.edu.springweb.model.Book;
import ru.edu.springweb.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
        private final BookService bookService;

        public BookController(BookService bookService) {
            this.bookService = bookService;
        }

        @GetMapping
        public List<Book> getAllBooks() {
            return bookService.getAllBooks();
        }

        @GetMapping("/{id}")
        public ResponseEntity<Book> getBookById(@PathVariable("id") int id) {
            return bookService.getBookById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }

        @PostMapping
        public ResponseEntity<Book> addBook(@RequestBody Book book) {
            Book createdBook = bookService.addBook(book);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
        }

        @PutMapping("/{id}")
        public ResponseEntity<Void> updateBook(@PathVariable("id") int id, @RequestBody Book book) {
            boolean updated = bookService.updateBook(id, book);
            return updated ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteBook(@PathVariable("id") Long id) {
            boolean deleted = bookService.deleteBook(id);
            return deleted ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
}
