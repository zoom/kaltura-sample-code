/* Copyright (c) 2018 Zoom Video Communications, Inc., All Rights Reserved */

package us.zoom.cms.controller;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;

@RestController
public class HealthController {

    @RequestMapping(path="/healthcheck", method=RequestMethod.GET)
    public HealthResult health(){
        HealthResult res = new HealthResult();
        return res;
    }


    @Data
    @XmlRootElement(name="healthcheck")
    public static class HealthResult{
        private String result = "OK";
        private boolean suspend = false;
    }
}
