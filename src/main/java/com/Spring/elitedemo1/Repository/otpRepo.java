package com.Spring.elitedemo1.Repository;

import com.Spring.elitedemo1.Model.Otp;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface otpRepo extends  MongoRepository<Otp, String>
{
    Optional<Otp> findByEmailAndCode(String email, String code);
    void deleteByEmail(String email);
}
