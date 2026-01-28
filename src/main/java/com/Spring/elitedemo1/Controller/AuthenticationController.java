package com.Spring.elitedemo1.Controller;

import com.Spring.elitedemo1.Model.User;
import com.Spring.elitedemo1.Services.AuthenticationServices;
import com.Spring.elitedemo1.dto.AuthResponse;
import com.Spring.elitedemo1.dto.LoginRequest;
import com.Spring.elitedemo1.dto.SignUpResponse;
import com.Spring.elitedemo1.dto.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthenticationController
{

    @Autowired
    private AuthenticationServices authService;
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signup(@RequestBody SignupRequest request) {
        SignUpResponse response = authService.signup(request);
        return ResponseEntity.ok(response);
    }

    // ✅ LOGIN (TOKEN GENERATED AFTER LOGIN)
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {

        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}
