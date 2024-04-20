package com.example.FitDoc.repository;

import com.example.FitDoc.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, String> {

    // You can define custom query methods here if needed

}

