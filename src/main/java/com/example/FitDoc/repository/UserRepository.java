package com.example.FitDoc.repository;

import com.example.FitDoc.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByUsername(String username);

    User findByUsername(String username);

}
