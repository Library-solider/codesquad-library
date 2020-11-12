package com.librarycodesquad.prod.controller;

import com.librarycodesquad.prod.controller.annotation.WithAdmin;
import com.librarycodesquad.prod.controller.annotation.WithGuest;
import com.librarycodesquad.prod.controller.annotation.WithUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithAnonymousUser
    public void 도서조회하기_not_OAuth2() throws Exception {
        mockMvc.perform(get("/v1/main"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithGuest
    public void 도서조회하기_OAuth2_GUEST() throws Exception {
        mockMvc.perform(get("/v1/books/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithUser
    public void 도서조회하기_OAuth2_USER() throws Exception {
        mockMvc.perform(get("/v1/books/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithAdmin
    public void 도서조회하기_OAuth2_ADMIN() throws Exception {
        mockMvc.perform(get("/v1/books/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void 도서빌리기_not_OAuth2() throws Exception {
        mockMvc.perform(post("/v1/books/1"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithGuest
    public void 도서빌리기_OAuth_GUEST() throws Exception {
        mockMvc.perform(post("/v1/books/1"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUser
    public void 도서빌리기_OAuth_USER() throws Exception {
        mockMvc.perform(post("/v1/books/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithAdmin
    public void 도서빌리기_OAuth_ADMIN() throws Exception {
        mockMvc.perform(post("/v1/books/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
