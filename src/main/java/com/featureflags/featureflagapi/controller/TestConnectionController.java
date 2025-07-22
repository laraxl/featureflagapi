package com.featureflags.featureflagapi.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test-db")
public class TestConnectionController {

    @PostMapping
    public String saveTestFlag() {
        return "Fake save successful (DB is disabled)";
    }

    @GetMapping
    public String getTestFlag() {
        return "Fake flag (DB is disabled)";
    }
}
