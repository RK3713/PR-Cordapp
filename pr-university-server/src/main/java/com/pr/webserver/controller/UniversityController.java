package com.pr.webserver.controller;

import com.pr.server.common.controller.CommonController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Define your API endpoints here.
 */
@RestController
@RequestMapping("/uni") // The paths for HTTP requests are relative to this base path.
@CrossOrigin
public class UniversityController extends CommonController{

    private final static Logger logger = LoggerFactory.getLogger(UniversityController.class);


    @CrossOrigin
    @GetMapping(value = "/templateendpoint", produces = "text/plain")
    private String templateendpoint() {
        return "Define an endpoint here.";
    }

    @CrossOrigin
    @GetMapping(value = "/name", produces = "text/plain")
    private String uniName() {
        return "Pune University";
    }
}