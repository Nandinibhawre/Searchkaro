package com.Spring.elitedemo1.Repository;

import com.Spring.elitedemo1.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface userRepo extends MongoRepository<User,String>
{
    // use built-in method
    Optional<User> findById(String userId);

    boolean existsById(String userid);
    boolean existsByEmail(String email);

    // if you still need email login
    Optional<User> findByEmail(String email);
}


