package com.Spring.elitedemo1.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProtectedServices
{
    @Autowired
    private ActivityRepository activityRepository;

    public Map<String, Object> getDashboard(String userId) {
        List<Map<String, Object>> result = activityRepository.getDashboardData(userId);
        return result.isEmpty() ? Map.of() : result.get(0);
    }
}
