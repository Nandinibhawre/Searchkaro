package com.Spring.elitedemo1.Controller;


import com.Spring.elitedemo1.Services.DashboardServices;
import com.Spring.elitedemo1.Util.JwtUtil;
import com.Spring.elitedemo1.dto.DashboardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DashboardController {

    @Autowired
    private JwtUtil jwtService;
    @Autowired
    private DashboardServices dashboardService;
    @GetMapping("/dashboard")
    public ResponseEntity<List<DashboardDTO>> dashboard(
            @RequestHeader("Authorization") String auth) {

        if (!auth.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid token");
        }

        String token = auth.substring(7);

        if (!jwtService.isTokenValid(token)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Token invalid");
        }

        // ✅ userId from JWT
        String userId = jwtService.extractUserId(token);

        System.out.println("JWT userId = " + userId);

        List<DashboardDTO> dashboardData =
                dashboardService.getDashboardData(userId);

        return ResponseEntity.ok(dashboardData);
    }


}