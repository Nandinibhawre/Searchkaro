package com.Spring.elitedemo1.Services;

import com.Spring.elitedemo1.Repository.CategoryRepo;
import com.Spring.elitedemo1.Repository.LocationRepo;
import com.Spring.elitedemo1.Repository.RatingRepo;
import com.Spring.elitedemo1.dto.CategoresDTO;

import com.Spring.elitedemo1.dto.LocationDTO;
import com.Spring.elitedemo1.dto.RatingDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardServices{

    @Autowired
    private LocationRepo locationRepo;

    @Autowired
    private RatingRepo ratingRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    // ✅ METHOD 1 – DTO-specific methods

    public List<LocationDTO> getLocationDTOsByUserId(String userId) {
        return locationRepo.findByUserId(userId)
                .stream()
                .map(l -> new LocationDTO(
                        l.getLocation(),
                        l.getRegion()
                ))
                .toList();
    }

    public List<RatingDTO> getRatingDTOsByUserId(String userId) {
        return ratingRepo.findByUserId(userId)
                .stream()
                .map(r -> new RatingDTO(
                        r.getRating()
                ))
                .toList();
    }

    public List<CategoresDTO> getCategoryDTOsByUserId(String userId) {
        return categoryRepo.findByUserId(userId)
                .stream()
                .map(c -> new CategoresDTO(
                        c.getCategoryName(),
                        c.getProductName()
                ))
                .toList();
    }
}

