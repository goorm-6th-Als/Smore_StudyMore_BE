package com.als.SMore.user.login.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

@Target({TYPE,METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JwtAuthority {
    int index() default 2;
}
