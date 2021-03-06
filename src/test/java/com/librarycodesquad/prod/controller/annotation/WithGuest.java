package com.librarycodesquad.prod.controller.annotation;

import org.springframework.security.test.context.support.WithMockUser;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithMockUser(username = "guest", roles = "GUEST")
public @interface WithGuest {
}
