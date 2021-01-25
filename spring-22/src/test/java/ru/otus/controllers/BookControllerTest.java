package ru.otus.controllers;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.rest.controllers.BookController;
import ru.otus.service.BookService;
import ru.otus.service.BookServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Configuration
    static class TestConfiguration {
        @Bean
        public BookService bookService() {
            return new BookServiceImpl(null, null, null);
        }

        @Bean
        public BookController bookController() {
            return new BookController(bookService());
        }
    }

    @Autowired
    MockMvc mvc;

    @Autowired
    private BookController bookController;

    @MockBean
    private BookService bookService;

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    public void testAuthenticatedOnAdmin() throws Exception {
        mvc.perform(get("/books"))
                .andExpect(status().isOk());
    }



}

