package com.app.springSecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@PreAuthorize("denyAll()") // this is necessary to enable annotations
public class TestAuthController {

    @GetMapping("/hello")
    @PreAuthorize("permitAll()") // public
    public String hello(){
        return "Hello World";
    }

    @GetMapping("hello-secured")
    @PreAuthorize("hasAuthority('CREATE')") // checks the authorities
    public String helloSecured (){
        return "Hello World Secured";
    }
}
