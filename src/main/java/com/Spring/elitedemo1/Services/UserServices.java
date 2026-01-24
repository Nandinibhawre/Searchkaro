package com.Spring.elitedemo1.Services;

import com.Spring.elitedemo1.Model.User;
import com.Spring.elitedemo1.Repository.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServices {

    @Autowired
    private userRepo userRepo;

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

//    public User getUserById(String id) {
//        return userRepo.findById(id)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//    }
    public User getUserById(String id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    public User updateUser(String id, User user) {
        User existing = getUserById(id);
        existing.setName(user.getName());
        existing.setEmail(user.getEmail());
        return userRepo.save(existing);
    }

    public void deleteUser(String id) {
        userRepo.deleteById(id);
    }
}
