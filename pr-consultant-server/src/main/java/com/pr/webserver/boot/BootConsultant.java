package com.pr.webserver.boot;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.springframework.boot.WebApplicationType.SERVLET;

/**
 * @author Ajinkya Pande & Rishi Kundu
 */

/**
 * Our Spring Boot application.
 */
@SpringBootApplication(scanBasePackages = {"com.pr.webserver.*","com.pr.server.common"})
public class BootConsultant {
    /**
     * Starts our Spring Boot application.
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(BootConsultant.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.setWebApplicationType(SERVLET);
        app.run(args);
    }
}