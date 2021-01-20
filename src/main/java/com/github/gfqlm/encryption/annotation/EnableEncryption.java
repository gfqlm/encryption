package com.github.gfqlm.encryption.annotation;

import com.github.gfqlm.encryption.configuration.EncryptionConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author GFQ
 * @date 2021/1/20
 * 作用于启动类,是否使用该框架
 * @see <a href='https://github.com/gfqlm/encryption'>https://github.com/gfqlm/encryption</a>
 */
@Inherited
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({EncryptionConfiguration.class})
public @interface EnableEncryption {
}
