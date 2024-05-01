package com.example.FitDoc.controller;

import com.example.FitDoc.model.Post;
import com.example.FitDoc.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import static com.example.FitDoc.controller.PostController.getPostResponseEntity;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private PostRepository postRepository;

    @PostMapping("/{id}")
    public ResponseEntity<Post> likePost(@PathVariable String id, @AuthenticationPrincipal OAuth2User user) {
        return getPostResponseEntity(id, user, postRepository);
    }
}
