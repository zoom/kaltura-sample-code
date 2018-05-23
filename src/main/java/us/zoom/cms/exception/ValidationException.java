/* Copyright (c) 2018 Zoom Video Communications, Inc., All Rights Reserved */

package us.zoom.cms.exception;

/**
 * Created by kavithakannan on 3/8/18.
 */

public class ValidationException extends BaseException {

    public ValidationException(String msg) {
        super(msg, ErrorCode.ERROR_VALIDATION.getErrorCode());
    }

    public ValidationException(String message, int errorCode) {
        super(message, errorCode);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause, ErrorCode.ERROR_VALIDATION.getErrorCode());
    }

    public ValidationException(String message, Throwable cause, int errorCode) {
        super(message, cause, errorCode);
    }

    public ValidationException(Throwable cause) {
        super(cause, ErrorCode.ERROR_VALIDATION.getErrorCode());
    }

    public ValidationException(Throwable cause, int errorCode) {
        super(cause);
        this.errCode = errorCode;
    }

    private static final long serialVersionUID = 1L;
}


