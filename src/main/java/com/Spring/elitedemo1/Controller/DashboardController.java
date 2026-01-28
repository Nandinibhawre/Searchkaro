package com.Spring.elitedemo1.Controller;


import com.Spring.elitedemo1.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class protectedController {

    @Autowired
    private JwtUtil jwtService;

    @GetMapping("/protected")
    public String protectedEndpoint(@RequestHeader("Authorization") String auth) {

        if (!auth.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        String token = auth.substring(7);

        if (!jwtService.isTokenValid(token)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return "Welcome to Dashboard 🎉";
    }

//    @GetMapping("/protected")
//    public String protectedEndpoint() {
//        return "Welcome to Dashboard 🎉";
//    }
}