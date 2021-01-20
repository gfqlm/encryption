package com.github.gfqlm.encryption.configuration;

import com.github.gfqlm.encryption.configuration.properties.EncryptionProperties;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author GFQ
 * @date 2021/1/20
 * @see <a href='https://github.com/gfqlm/encryption'>https://github.com/gfqlm/encryption</a>
 * 加密自动装配类
 */
@Configuration
@Component
@EnableAutoConfiguration
@EnableConfigurationProperties(EncryptionProperties.class)
public class EncryptionConfiguration {
}
