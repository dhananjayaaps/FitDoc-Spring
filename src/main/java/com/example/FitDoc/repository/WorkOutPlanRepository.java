package com.example.FitDoc.repository;

import com.example.FitDoc.model.MealPlan;
import com.example.FitDoc.model.WorkOutPlan;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WorkOutPlanRepository extends MongoRepository<WorkOutPlan, String> {

    // You can define custom query methods here if needed

}

