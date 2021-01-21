package com.github.gfqlm.encryption.advice;


import cn.hutool.core.codec.Base64;
import cn.hutool.json.JSONUtil;
import com.github.gfqlm.encryption.algorithm.EncryptAlgorithm;
import com.github.gfqlm.encryption.configuration.properties.EncryptionProperties;
import com.github.gfqlm.encryption.handler.ResultHandler;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.net.URI;

/**
 * @author GFQ
 * @date 2021/1/20
 * @see <a href='https://github.com/gfqlm/encryption'>https://github.com/gfqlm/encryption</a>
 * 响应数据加密处理类
 */
@Slf4j
@ControllerAdvice
public class EncryptionResponseAdvice implements ResponseBodyAdvice<Object> {

    @Autowired
    private EncryptionProperties encryptionProperties;

    @Autowired
    private EncryptAlgorithm encryptAlgorithm;

    @Autowired
    private ResultHandler resultHandler;


    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {

        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (encryptionProperties.isDebug()) {
            // 如果是debug模式,则不进行加密操作
            return body;
        }
        URI uri = request.getURI();
        log.info("uri:{}", uri.getPath());
        String strBody = JSONUtil.toJsonStr(body);
        try {
            String encrypt = encryptAlgorithm.encrypt(strBody, encryptionProperties.getSecretKey());
            Object object = resultHandler.getResult(encrypt);
            return object;
        } catch (Exception e) {
            log.error("EncryptionResponseAdvice.beforeBodyWrite.encrypt.exception", e);
        }
        return body;
    }
}
