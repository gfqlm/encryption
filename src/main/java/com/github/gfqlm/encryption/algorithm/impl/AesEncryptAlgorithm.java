package com.github.gfqlm.encryption.algorithm.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.github.gfqlm.encryption.algorithm.EncryptAlgorithm;
import com.github.gfqlm.encryption.exception.ValidSecurityKeyException;

/**
 * @author GFQ
 * @date 2021/1/21
 * @see <a href='https://github.com/gfqlm/encryption'>https://github.com/gfqlm/encryption</a>
 * 内置 AES加密类算法
 */
public class AesEncryptAlgorithm implements EncryptAlgorithm {

    /**
     * 加密
     *
     * @param content    加密内容
     * @param encryptKey 加密Key
     * @return
     */
    @Override
    public String encrypt(String content, String encryptKey) {
        if (StrUtil.length(encryptKey) != 16) {
            throw new ValidSecurityKeyException("默认AES加密算法秘钥长度不合法","invalid_aes_key");
        }
        byte[] decode = encryptKey.getBytes();
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, decode);
        String decryptSecret = aes.encryptHex(content);
        return decryptSecret;
    }

    /**
     * 解密
     *
     * @param encryptStr 解密字符串
     * @param decryptKey 解密Key
     * @return
     */
    @Override
    public String decrypt(String encryptStr, String decryptKey) {
        if (StrUtil.length(decryptKey) != 16) {
            throw new ValidSecurityKeyException("默认AES解密算法秘钥长度不合法","invalid_aes_key");
        }
        byte[] decode = decryptKey.getBytes();
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, decode);
        String decryptSecret = aes.decryptStr(encryptStr);
        return decryptSecret;
    }
}
