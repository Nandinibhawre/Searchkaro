package com.Spring.elitedemo1.Services;

import com.Spring.elitedemo1.Model.Dashboard;
import com.Spring.elitedemo1.Repository.DashboardRepo;
import com.Spring.elitedemo1.dto.DashboardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardServices
{
    @Autowired
    private DashboardRepo DashboardRepo;
    public List<DashboardDTO> getDashboardData(String userId) {

        List<Dashboard>  dashboards = DashboardRepo.findByUserId(userId);

        return dashboards.stream()
                .map(a -> new DashboardDTO(
                        a.getCategory(),
                        a.getLocation(),
                        a.getRating()
                ))
                .collect(Collectors.toList());
    }
}
