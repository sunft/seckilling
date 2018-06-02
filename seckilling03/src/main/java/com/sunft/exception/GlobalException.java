package com.sunft.exception;

import com.sunft.result.CodeMsg;

/**
 * Created by sunft on 2018/6/2.
 * 全局异常
 */
public class GlobalException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private CodeMsg cm;

    public GlobalException(CodeMsg cm) {
        super(cm.toString());
        this.cm = cm;
    }

    public CodeMsg getCm() {
        return cm;
    }

}
