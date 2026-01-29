package com.Spring.elitedemo1.Controller;


import com.Spring.elitedemo1.Services.DashboardServices;
import com.Spring.elitedemo1.Util.JwtUtil;
import com.Spring.elitedemo1.dto.DashboardResponse;
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

    @GetMapping("/dashboard/{userId}")
    public ResponseEntity<DashboardResponse> dashboard(
            @RequestHeader("Authorization") String auth,
            @PathVariable String userId) {

        // 🔐 TOKEN CHECK (YOUR CODE)
        if (!auth.startsWith("Bearer ")) {  
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid token");
        }

        String token = auth.substring(7);

        if (!jwtService.isTokenValid(token)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Token invalid");
        }

        // 🔐 Extract userId from token
        String tokenUserId = jwtService.extractUserId(token);

        // 🔐 Ensure token user == path user
        if (!tokenUserId.equals(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Unauthorized access");
        }

        // 📊 DASHBOARD DATA (MY CODE)
        DashboardResponse response = new DashboardResponse(
                dashboardService.getCategoryDTOsByUserId(userId),
                dashboardService.getLocationDTOsByUserId(userId),
                dashboardService.getRatingDTOsByUserId(userId)
        );

        return ResponseEntity.ok(response);
    }
}