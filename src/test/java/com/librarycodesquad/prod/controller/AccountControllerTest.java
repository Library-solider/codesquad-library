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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithAnonymousUser
    public void 마이페이지_NOT_OAUTH2() throws Exception {
        mockMvc.perform(get("/v1/users/mypage"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithGuest
    public void 마이페이지_OAUTH_GUEST() throws Exception {
        mockMvc.perform(get("/v1/users/mypage"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUser
    public void 마이페이지_OAUTH_USER() throws Exception {
        mockMvc.perform(get("/v1/users/mypage"))
                .andExpect(status().isOk());
    }

    @Test
    @WithAdmin
    public void 마이페이지_OAUTH_ADMIN() throws Exception {
        mockMvc.perform(get("/v1/users/mypage"))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void 프로필_NOT_OAUTH2() throws Exception {
        mockMvc.perform(get("/v1/users/profile"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithGuest
    public void 프로필_OAUTH_GUEST() throws Exception {
        mockMvc.perform(get("/v1/users/profile"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
