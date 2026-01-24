package com.Spring.elitedemo1.Controller;

import com.Spring.elitedemo1.Repository.userRepo;
import com.Spring.elitedemo1.Services.OtpService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class PasswordController {

    @Autowired
    private OtpService otpService;

    @Autowired
    private userRepo userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Step 1: Request OTP
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        if(!userRepository.existsByEmail(email)) {
            return ResponseEntity.badRequest().body("Email not registered");
        }
        otpService.generateOtp(email);
        return ResponseEntity.ok("OTP sent to email");
    }


    // Step 2: Reset Password
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestParam String email,
            @RequestParam String otp,
            @RequestParam String newPassword) {

        if(!otpService.verifyOtp(email, otp)) {
            return ResponseEntity.badRequest().body("Invalid or expired OTP");
        }

        var user = userRepository.findByEmail(email).get();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        otpService.clearOtp(email);

        return ResponseEntity.ok("Password reset successfully");
    }
}
