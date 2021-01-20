package com.github.gfqlm.encryption.annotation;


import java.lang.annotation.*;

/**
 * @author GFQ
 * <p>请求参数数据解密动作</p>
 * @see <a href='https://github.com/gfqlm/encryption'>https://github.com/gfqlm/encryption</a>
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Decrypt {
}
