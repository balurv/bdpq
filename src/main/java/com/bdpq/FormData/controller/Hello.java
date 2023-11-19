package com.bdpq.FormData.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greet")
public class Hello {
    @GetMapping
    public ResponseEntity<String> greet(){
        return new ResponseEntity<>("Helloo world!", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> greet1(){
        return new ResponseEntity<>("Heelo world", HttpStatus.OK);
    }
}
