package com.github.gfqlm.encryption.exception;

import lombok.Data;

/**
 * @author GFQ
 * @date 2021/1/22
 * @see <a href='https://github.com/gfqlm/encryption'>https://github.com/gfqlm/encryption</a>
 */
@Data
public class ValidSecurityKeyException extends RuntimeException {

    private String message;

    private String status;

    public ValidSecurityKeyException(String message, String status) {
        super(message);
        this.message = message;
        this.status = status;
    }
}
