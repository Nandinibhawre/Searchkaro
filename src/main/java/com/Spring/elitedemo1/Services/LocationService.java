package com.Spring.elitedemo1.Services;

import com.Spring.elitedemo1.Model.Location;
import com.Spring.elitedemo1.Repository.LocationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    @Autowired
    private LocationRepo repo;

    public List<Location> getByUser(String userId) {
        return repo.findByUserId(userId);
    }

    public Location create(Location location, String userId) {
        location.setUserId(userId);
        return repo.save(location);
    }

    public Location update(String id, Location data, String userId) {
        Location loc = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found"));

        if (!loc.getUserId().equals(userId))
            throw new RuntimeException("Unauthorized");

        loc.setRole(data.getRole());
        loc.setLocation(data.getLocation());
        loc.setRegion(data.getRegion());
        loc.setPopular(data.getPopular());

        return repo.save(loc);
    }

    public void delete(String id, String userId) {
        Location loc = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found"));

        if (!loc.getUserId().equals(userId))
            throw new RuntimeException("Unauthorized");

        repo.delete(loc);
    }
}
