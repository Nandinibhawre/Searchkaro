package com.Spring.elitedemo1.Controller;

import com.Spring.elitedemo1.Model.Location;
import com.Spring.elitedemo1.Services.LocationService;
import com.Spring.elitedemo1.Util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class LocationController {

    @Autowired
    private LocationService service;

    @Autowired
    private JwtUtil jwtUtil;

    private String getUserId(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        return jwtUtil.extractUserId(token);
    }

    @GetMapping("/location")
    public List<Location> getAll(HttpServletRequest request) {
        return service.getByUser(getUserId(request));
    }

    @PostMapping("/postLocation")
    public Location create(
            @RequestBody Location location,
            HttpServletRequest request) {
        return service.create(location, getUserId(request));
    }


    @GetMapping("/{id}")
    public Location getById(
            @PathVariable String id,
            HttpServletRequest request
    ) {
        System.out.println("User ID from request: " + getUserId(request));
        return service.getById(id, getUserId(request));
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable String id,
            HttpServletRequest request) {
        service.delete(id, getUserId(request));
    }

}
