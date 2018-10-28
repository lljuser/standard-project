package com.heyi.core.netty.guava;
import java.lang.annotation.*;

@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD,ElementType.TYPE})
public @interface MyAnnotation {
    public String[] value();
    public String key() default "";
}
