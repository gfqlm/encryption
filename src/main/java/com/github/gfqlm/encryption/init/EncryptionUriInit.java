package com.github.gfqlm.encryption.init;

import cn.hutool.core.collection.CollUtil;
import com.github.gfqlm.encryption.annotation.Decryption;
import com.github.gfqlm.encryption.annotation.Encryption;
import com.github.gfqlm.encryption.constant.HttpMethodTypePrefixConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author GFQ
 * @date 2021/1/20
 * @see <a href='https://github.com/gfqlm/encryption'>https://github.com/gfqlm/encryption</a>
 * 项目启动时加载所有添加加密、解密注解的URI
 */
@Slf4j
public class EncryptionUriInit implements ApplicationContextAware {


    /**
     * 需要对响应内容进行加密的接口URI<br>
     * 比如：/user/list<br>
     */
    public static List<String> responseEncryptionUri = new ArrayList<String>();

    /**
     * 需要对请求内容进行解密的接口URI<br>
     * 比如：/user/list<br>
     */
    public static List<String> requestDecryptionUri = new ArrayList<String>();

    private String contextPath;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.contextPath = applicationContext.getEnvironment().getProperty("server.servlet.context-path");
        Map<String, Object> beanMap = applicationContext.getBeansWithAnnotation(Controller.class);
        initEncryptionUrl(beanMap);
    }

    /**
     * 初始化需要加密
     *
     * @param beanMap
     */
    private void initEncryptionUrl(Map<String, Object> beanMap) {
        if (CollUtil.isNotEmpty(beanMap)) {
            for (Object bean : beanMap.values()) {
                Class<?> clazz = bean.getClass();
                Method[] methods = clazz.getMethods();
                if (null != methods) {
                    for (Method method : methods) {
                        Encryption encrypt = AnnotationUtils.findAnnotation(method, Encryption.class);
                        if (encrypt != null) {
                            // 注解中的URI优先级高
                            String uri = encrypt.value();
                            if (!StringUtils.hasText(uri)) {
                                uri = getUri(clazz, method);
                            }
                            log.info("Encrypt URI: {}", uri);
                            responseEncryptionUri.add(uri);
                        }

                        Decryption decrypt = AnnotationUtils.findAnnotation(method, Decryption.class);
                        if (decrypt != null) {
                            String uri = decrypt.value();
                            if (!StringUtils.hasText(uri)) {
                                uri = getUri(clazz, method);
                            }
                            log.info("Decryption URI: {}", uri);
                            requestDecryptionUri.add(uri);
                        }
                    }
                }
            }
        }
    }


    private String getUri(Class<?> clz, Method method) {
        String methodType = "";
        StringBuilder uri = new StringBuilder();

        RequestMapping reqMapping = AnnotationUtils.findAnnotation(clz, RequestMapping.class);
        if (reqMapping != null) {
            uri.append(formatUri(reqMapping.value()[0]));
        }

        GetMapping getMapping = AnnotationUtils.findAnnotation(method, GetMapping.class);
        PostMapping postMapping = AnnotationUtils.findAnnotation(method, PostMapping.class);
        RequestMapping requestMapping = AnnotationUtils.findAnnotation(method, RequestMapping.class);
        PutMapping putMapping = AnnotationUtils.findAnnotation(method, PutMapping.class);
        DeleteMapping deleteMapping = AnnotationUtils.findAnnotation(method, DeleteMapping.class);

        if (getMapping != null) {
            methodType = HttpMethodTypePrefixConstant.GET;
            uri.append(formatUri(getMapping.value()[0]));

        } else if (postMapping != null) {
            methodType = HttpMethodTypePrefixConstant.POST;
            uri.append(formatUri(postMapping.value()[0]));

        } else if (putMapping != null) {
            methodType = HttpMethodTypePrefixConstant.PUT;
            uri.append(formatUri(putMapping.value()[0]));

        } else if (deleteMapping != null) {
            methodType = HttpMethodTypePrefixConstant.DELETE;
            uri.append(formatUri(deleteMapping.value()[0]));

        } else if (requestMapping != null) {
            RequestMethod requestMethod = RequestMethod.GET;
            if (requestMapping.method().length > 0) {
                requestMethod = requestMapping.method()[0];
            }

            methodType = requestMethod.name().toLowerCase() + ":";
            uri.append(formatUri(requestMapping.value()[0]));

        }

        if (StringUtils.hasText(this.contextPath) && !"/".equals(this.contextPath)) {
            if (this.contextPath.endsWith("/")) {
                this.contextPath = this.contextPath.substring(0, this.contextPath.length() - 1);
            }
            return methodType + this.contextPath + uri.toString();
        }

        return methodType + uri.toString();
    }


    private String formatUri(String uri) {
        if (uri.startsWith("/")) {
            return uri;
        }
        return "/" + uri;
    }


}
