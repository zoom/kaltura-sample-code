/* Copyright (c) 2018 Zoom Video Communications, Inc., All Rights Reserved */

package us.zoom.cms.exception;

import java.io.Serializable;

public class BaseException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -5745331324487158202L;

    protected int errCode = -1;

    public BaseException(String msg) {
        super(msg);
    }

    public BaseException(String message, int errorCode) {
        super(message);
        this.errCode = errorCode;
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(String message, Throwable cause, int errorCode) {
        super(message, cause);
        this.errCode = errorCode;
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(Throwable cause, int errorCode) {
        super(cause);
        this.errCode = errorCode;
    }

    public int getErrCode() {
        return this.errCode;
    }
}



