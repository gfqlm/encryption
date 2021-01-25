package com.github.gfqlm.encryption.advice;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.io.IoUtil;
import com.github.gfqlm.encryption.algorithm.EncryptAlgorithm;
import com.github.gfqlm.encryption.configuration.properties.EncryptionProperties;
import com.github.gfqlm.encryption.handler.ResultHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * @author GFQ
 * @date 2021/1/20
 * @see <a href='https://github.com/gfqlm/encryption'>https://github.com/gfqlm/encryption</a>
 * 请求参数进行加密处理类  只处理@RequestBody注解的参数
 */
@Slf4j
@ControllerAdvice
public class DecryptionRequestBodyAdvice implements RequestBodyAdvice {

    @Autowired
    private EncryptionProperties encryptionProperties;

    @Autowired
    private EncryptAlgorithm encryptAlgorithm;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        try {
            return new DecryptionHttpInputMessage(inputMessage, encryptionProperties.getSecretKey(), "UTF-8");
        } catch (Exception e) {
            log.error("DecryptionRequestAdvice.beforeBodyRead.exception", e);
        }
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    /**
     * 请求的解密实现类
     */
    class DecryptionHttpInputMessage implements HttpInputMessage {

        private HttpHeaders headers;
        private InputStream body;

        public DecryptionHttpInputMessage(HttpInputMessage httpInputMessage, String key, String encode) throws Exception {
            this.headers = httpInputMessage.getHeaders();
            this.body = decrypt(httpInputMessage, key, encode);
        }


        /**
         * 对请求的@requestBody的数据进行解密
         *
         * @param httpInputMessage
         * @param key              解密的key值
         * @param encode           编码集,UTF-8
         * @return
         * @throws Exception
         */
        public InputStream decrypt(HttpInputMessage httpInputMessage, String key, String encode) throws Exception {
            InputStream inputStream = httpInputMessage.getBody();
            String content = IoUtil.read(inputStream, encode);
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            String decryptBody = "";
            if (content.startsWith("{")) {
                decryptBody = content;
            } else {
                decryptBody = encryptAlgorithm.decrypt(content, key);
            }
            stopWatch.stop();
            log.info("【DecryptionHttpInputMessage】-【decrypt】 time:{}", stopWatch.getTotalTimeMillis());
            return IoUtil.toStream(decryptBody, encode);
        }

        @Override
        public InputStream getBody() throws IOException {

            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }
    }
}
