/* Copyright (c) 2018 Zoom Video Communications, Inc., All Rights Reserved */

package us.zoom.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import us.zoom.cms.exception.BadServiceException;

/**
 * Created by kavithakannan on 3/9/18.
 */

@org.springframework.web.bind.annotation.ControllerAdvice
public class ExceptionController {

    /**
     * Handles ResourceExceptions for the SpringMVC controllers.
     * @param e SpringMVC controller exception.
     * @return http response entity
     * @see ExceptionHandler
     */
    @ExceptionHandler(BadServiceException.class)
    public ResponseEntity handleException(BadServiceException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }
}
