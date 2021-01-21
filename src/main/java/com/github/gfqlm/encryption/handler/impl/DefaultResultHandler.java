package com.github.gfqlm.encryption.handler.impl;

import com.github.gfqlm.encryption.handler.ResultHandler;

/**
 * @author GFQ
 * @date 2021/1/21
 * @see <a href='https://github.com/gfqlm/encryption'>https://github.com/gfqlm/encryption</a>
 * <p>
 * 默认结果处理器
 */
public class DefaultResultHandler implements ResultHandler {

    /**
     * 啥也不处理,给啥还啥
     *
     * @param result
     * @return object
     */
    @Override
    public Object getResult(String result) {
        return result;
    }
}
