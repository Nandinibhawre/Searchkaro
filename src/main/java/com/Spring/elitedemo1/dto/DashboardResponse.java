package com.Spring.elitedemo1.dto;
import com.Spring.elitedemo1.Model.Categries;
import com.Spring.elitedemo1.Model.Location;
import com.Spring.elitedemo1.Model.Rating;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DashboardResponse
{


    private List<CategoresDTO> categories;
    private List<LocationDTO> locations;
    private List<RatingDTO> ratings;

    public DashboardResponse(
            List<CategoresDTO> categories,
            List<LocationDTO> locations,
            List<RatingDTO> ratings
    ) {
        this.categories = categories;
        this.locations = locations;
        this.ratings = ratings;
    }

    public List<CategoresDTO> getCategories() {
        return categories;
    }

    public List<LocationDTO> getLocations() {
        return locations;
    }

    public List<RatingDTO> getRatings() {
        return ratings;
    }
    }
