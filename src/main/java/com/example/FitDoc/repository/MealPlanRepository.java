package com.example.FitDoc.repository;

import com.example.FitDoc.model.MealPlan;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MealPlanRepository extends MongoRepository<MealPlan, String> {

    // You can define custom query methods here if needed

}

