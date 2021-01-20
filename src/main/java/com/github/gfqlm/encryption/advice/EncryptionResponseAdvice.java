package com.github.gfqlm.encryption.advice;


import cn.hutool.core.codec.Base64;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
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


    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {

        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        URI uri = request.getURI();
        log.info("uri:{}", uri.getPath());
        String bodyStr = JSONUtil.toJsonStr(body);

        String dataStr = JSONUtil.toJsonStr(bodyStr);
        String encodeBody = Base64.encode(dataStr);
        return encodeBody;
    }
}
