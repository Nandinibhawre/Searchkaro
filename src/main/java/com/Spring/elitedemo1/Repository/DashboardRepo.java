package com.Spring.elitedemo1.Repository;

import com.Spring.elitedemo1.Model.Dashboard;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DashboardRepo extends MongoRepository<Dashboard,String>

{
    List<Dashboard> findByUserId(String userId);
}
