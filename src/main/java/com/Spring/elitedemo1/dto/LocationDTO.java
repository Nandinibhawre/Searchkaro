package com.Spring.elitedemo1.dto;

public class LocationDTO {

    private String location;
    private String region;

    public LocationDTO(String location, String region) {
        this.location = location;
        this.region = region;
    }

    public String getLocation() {
        return location;
    }

    public String getRegion() {
        return region;
    }
    }
