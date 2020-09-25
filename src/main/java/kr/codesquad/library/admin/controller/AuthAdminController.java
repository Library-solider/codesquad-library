package kr.codesquad.library.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class AuthAdminController {

    @GetMapping("/login")
    public String access() {
        return "access-admin";
    }
}
