/* Copyright (c) 2018 Zoom Video Communications, Inc., All Rights Reserved */

package us.zoom.cms.exception;

/**
 * Created by kavithakannan on 4/25/18.
 */

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class TokenException extends BaseException {

    private static final long serialVersionUID = 1L;

    private final static int errorCode = 1401;

    public TokenException(String msg) {
        super(msg, errorCode);
    }

    public TokenException(String message, int errorCode) {
        super(message, errorCode);
    }

    public TokenException(String message, Throwable cause) {
        super(message, cause, errorCode);
    }

    public TokenException(String message, Throwable cause, int errorCode) {
        super(message, cause, errorCode);
    }

    public TokenException(Throwable cause) {
        super(cause, errorCode);
    }

    public TokenException(Throwable cause, int errorCode) {
        super(cause);
        this.errCode = errorCode;
    }
}

