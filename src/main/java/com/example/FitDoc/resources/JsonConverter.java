package com.example.FitDoc.resources;

import com.example.FitDoc.model.Post;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.stream.Collectors;

public class JsonConverter {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String convertPostsToJson(List<Post> posts) throws JsonProcessingException {
        return objectMapper.writeValueAsString(posts);
    }
}