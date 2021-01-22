package com.github.gfqlm.encryption.configuration;

import com.github.gfqlm.encryption.advice.EncryptionRequestAdvice;
import com.github.gfqlm.encryption.advice.EncryptionResponseAdvice;
import com.github.gfqlm.encryption.algorithm.EncryptAlgorithm;
import com.github.gfqlm.encryption.algorithm.impl.AesEncryptAlgorithm;
import com.github.gfqlm.encryption.configuration.properties.EncryptionProperties;
import com.github.gfqlm.encryption.handler.ResultHandler;
import com.github.gfqlm.encryption.handler.impl.DefaultResultHandler;
import com.github.gfqlm.encryption.init.EncryptionUriInit;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author GFQ
 * @date 2021/1/20
 * @see <a href='https://github.com/gfqlm/encryption'>https://github.com/gfqlm/encryption</a>
 * 加密自动装配类
 */
@Configuration
@EnableConfigurationProperties(EncryptionProperties.class)
public class EncryptionConfiguration {

    @Bean
    @ConditionalOnMissingBean(EncryptionRequestAdvice.class)
    public EncryptionRequestAdvice getEncryptionRequestAdvice() {
        return new EncryptionRequestAdvice();
    }


    @Bean
    @ConditionalOnMissingBean(EncryptionResponseAdvice.class)
    public EncryptionResponseAdvice getEncryptionResponseAdvice() {
        return new EncryptionResponseAdvice();
    }

    @Bean
    @ConditionalOnMissingBean(EncryptionUriInit.class)
    public EncryptionUriInit getEncryptionUriInit() {
        return new EncryptionUriInit();
    }

    @Bean
    @ConditionalOnMissingBean(ResultHandler.class)
    public ResultHandler getResultHandler() {
        return new DefaultResultHandler();
    }

    @Bean
    @ConditionalOnMissingBean(EncryptAlgorithm.class)
    public EncryptAlgorithm getEncryptAlgorithm() {
        return new AesEncryptAlgorithm();
    }
}
