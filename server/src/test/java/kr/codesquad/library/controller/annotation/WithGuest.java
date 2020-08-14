package kr.codesquad.library.controller.annotation;

import org.springframework.security.test.context.support.WithMockUser;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithMockUser(username = "sunny", roles = "GUEST")
public @interface WithGuest {
}
