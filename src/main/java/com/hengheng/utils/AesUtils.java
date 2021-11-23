package com.hengheng.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hongbo.pan
 * @date 2021/11/23
 */
@Slf4j
public class AesUtils {

    private final static AES aes = SecureUtil.aes(
            SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue(),
            "1234567890123456".getBytes()).getEncoded());

    public static String encrypt(String data) {
        if (StrUtil.isNotBlank(data)) {
            return aes.encryptBase64(data);
        }
        return data;
    }

    /**
     * 兼容处理，解密失败返回原值
     * @param data
     * @return
     */
    public static String decrypt(String data) {
        try {
            data = aes.decryptStr(Base64.decode(data));
        } catch (Exception e) {
            log.error("aes解密失败", e);
        }
        return data;
    }
}
