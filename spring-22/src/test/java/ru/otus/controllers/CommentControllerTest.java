package ru.otus.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.rest.controllers.CommentController;
import ru.otus.service.CommentService;
import ru.otus.service.CommentServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Configuration
    static class TestConfiguration {
        @Bean
        public CommentService commentService() {
            return new CommentServiceImpl(null, null);
        }
    }

    @Autowired
    MockMvc mvc;

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    public void testAuthenticatedOnAdmin() throws Exception {

        mvc.perform(get("/books/10/comments"))
                .andExpect(status().isNotFound());
    }
}
