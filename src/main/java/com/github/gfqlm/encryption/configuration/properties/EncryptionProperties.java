package com.github.gfqlm.encryption.configuration.properties;

import com.github.gfqlm.encryption.init.EncryptionUriInit;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author GFQ
 * @date 2021/1/20
 * @see <a href='https://github.com/gfqlm/encryption'>https://github.com/gfqlm/encryption</a>
 * 加密相关的配置信息
 */
@ConfigurationProperties(prefix = "ggzj.encrypt")
public class EncryptionProperties {

    /**
     * 秘钥
     */
    private String secretKey;

    /**
     * 开启调试模式，调试模式下不进行加解密操作，用于像Swagger这种在线API测试场景
     */
    private boolean debug = false;


    /**
     * 需要对响应内容进行加密的接口URI<br>
     * 比如：/user/list<br>
     */
    private List<String> responseEncryptionUri = new ArrayList<String>();

    /**
     * 需要对请求内容进行解密的接口URI<br>
     * 比如：/user/list<br>
     */
    private List<String> requestDecryptionUri = new ArrayList<String>();

    public EncryptionProperties() {
    }

    public EncryptionProperties(String secretKey, boolean debug, List<String> responseEncryptionUri, List<String> requestDecryptionUri) {
        this.secretKey = secretKey;
        this.debug = debug;
        this.responseEncryptionUri = responseEncryptionUri;
        this.requestDecryptionUri = requestDecryptionUri;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public List<String> getResponseEncryptionUri() {
        // 通过属性的getter方法合并配置的uri与注解的uri集合
        return Stream.of(responseEncryptionUri, EncryptionUriInit.responseEncryptionUri).flatMap(Collection::stream).collect(Collectors.toList());
    }

    public void setResponseEncryptionUri(List<String> responseEncryptionUri) {
        this.responseEncryptionUri = responseEncryptionUri;
    }

    public List<String> getRequestDecryptionUri() {
        // 通过属性的getter方法合并配置的uri与注解的uri集合
        return Stream.of(requestDecryptionUri, EncryptionUriInit.requestDecryptionUri).flatMap(Collection::stream).collect(Collectors.toList());
    }

    public void setRequestDecryptionUri(List<String> requestDecryptionUri) {
        this.requestDecryptionUri = requestDecryptionUri;
    }
}
