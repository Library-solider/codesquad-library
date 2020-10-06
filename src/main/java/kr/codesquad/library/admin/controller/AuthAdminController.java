package kr.codesquad.library.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class AuthAdminController {

    @GetMapping("/login")
    public String access() {
        return "access-admin";
    }

    @GetMapping("/login/failure")
    public String accessFailure() { return "access-failure"; }
}
