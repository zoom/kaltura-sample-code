/* Copyright (c) 2018 Zoom Video Communications, Inc., All Rights Reserved */

package us.zoom.cms.exception;

/**
 * Created by kavithakannan on 3/8/18.
 */

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorCode {

    ERROR_VALIDATION(1500);



    private int errorCode;

    private ErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @JsonValue
    public int getErrorCode() {
        return errorCode;
    }
}
