package com.github.gfqlm.encryption.algorithm.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.github.gfqlm.encryption.algorithm.EncryptAlgorithm;

/**
 * @author GFQ
 * @date 2021/1/21
 * @see <a href='https://github.com/gfqlm/encryption'>https://github.com/gfqlm/encryption</a>
 * 内置 AES加密类算法
 */
public class AesEncryptAlgorithm implements EncryptAlgorithm {

    /**
     * 加密
     * @param content    加密内容
     * @param encryptKey 加密Key
     * @return
     * @throws Exception
     */
    @Override
    public String encrypt(String content, String encryptKey) throws Exception {
        byte[] decode = Base64.decode(encryptKey);
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, decode);
        String decryptSecret = aes.encryptHex(content);
        return decryptSecret;
    }

    /**
     * 解密
     * @param encryptStr 解密字符串
     * @param decryptKey 解密Key
     * @return
     * @throws Exception
     */
    @Override
    public String decrypt(String encryptStr, String decryptKey) throws Exception {
        byte[] decode = Base64.decode(decryptKey);
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, decode);
        String decryptSecret = aes.decryptStr(encryptStr);
        return decryptSecret;
    }
}
