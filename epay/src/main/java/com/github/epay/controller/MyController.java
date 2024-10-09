package com.github.epay.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Raman Haurylau
 */
@RestController
@RequestMapping("test")
public class MyController {

    private final Logger log = LoggerFactory.getLogger(MyController.class);

    @GetMapping("/whoami")
    //@PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> index() {
        log.info("{}", "hi");
        return ResponseEntity.ok("Hello ${authentication.name}. Roles: ${authentication.authorities}");
    }
}
