package ru.edu.springweb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.edu.springweb.config.WebConfig;
import ru.edu.springweb.controller.BookController;
import ru.edu.springweb.model.Book;
import ru.edu.springweb.service.BookService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig(WebConfig.class)
public class BookControllerTest {

    private MockMvc mockMvc;
    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookService = mock(BookService.class);
        BookController bookController = new BookController(bookService);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    void testGetAllBooks() throws Exception {
        when(bookService.getAllBooks()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));
    }

    @Test
    void testAddBook() throws Exception {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("New Book");
        book.setAuthor("Author");

        when(bookService.addBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"New Book\",\"author\":\"Author\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("New Book"))
                .andExpect(jsonPath("$.author").value("Author"));
    }

    @Test
    void testGetBookById_NotFound() throws Exception {
        when(bookService.getBookById(1)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteBook_Success() throws Exception {
        Long bookId = 1L;

        when(bookService.deleteBook(bookId)).thenReturn(true);

        mockMvc.perform(delete("/books/{id}", bookId))
                .andExpect(status().isNoContent());
    }
}
