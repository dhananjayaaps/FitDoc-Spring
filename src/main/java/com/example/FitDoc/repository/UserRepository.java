package com.example.FitDoc.repository;

import com.example.FitDoc.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<User, String> {
    // Add custom methods if needed
}
