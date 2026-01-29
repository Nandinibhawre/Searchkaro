package com.Spring.elitedemo1.Services;

import com.Spring.elitedemo1.Model.Location;
import com.Spring.elitedemo1.Repository.LocationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    private final LocationRepo repo;

    public LocationService(LocationRepo repo) {
        this.repo = repo;
    }

    //get all location
    public List<Location> getByUser(String userId) {
        return repo.findByUserId(userId);
    }

    //create location
    public Location create(Location location, String userId) {
        location.setUserId(userId);
        return repo.save(location);
    }

  //Delete
    public void delete(String id, String userId) {
        Location loc = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found"));

        if (!loc.getUserId().equals(userId))
            throw new RuntimeException("Unauthorized");

        repo.delete(loc);
    }
}
