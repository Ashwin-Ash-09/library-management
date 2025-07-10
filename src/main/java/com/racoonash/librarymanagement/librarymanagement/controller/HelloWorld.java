package com.racoonash.librarymanagement.librarymanagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {

    // This controller handles a simple "Hello World" endpoint.
    // It responds to GET requests at "/api/hello" and returns a string "Hello World".
    // This endpoint is publicly accessible and does not require authentication.
    // It is typically used for testing purposes to ensure that the application is running correctly.
    @GetMapping("/api/hello")
    public String helloWorld() {
        return "Hello World";
    }

}
