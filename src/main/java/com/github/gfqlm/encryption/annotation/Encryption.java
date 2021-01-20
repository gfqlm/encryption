package com.github.gfqlm.encryption.annotation;

import java.lang.annotation.*;

/**
 * @author GFQ
 * <p>响应数据加</p>
 * @see <a href='https://github.com/gfqlm/encryption'>https://github.com/gfqlm/encryption</a>
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Encryption {

    String value() default "";
}
