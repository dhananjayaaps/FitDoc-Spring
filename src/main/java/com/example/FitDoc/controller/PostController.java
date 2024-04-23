package com.example.FitDoc.controller;

import com.example.FitDoc.model.Post;
import com.example.FitDoc.repository.PostRepository;
import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.JsonArray;
import com.nimbusds.jose.shaded.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping()
    public String getAllPosts() {
        List<Post> posts = postRepository.findAll();
        Gson gson = new Gson();
        String jsonPosts = gson.toJson(posts);
        return jsonPosts;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable String id) {
        Post post = postRepository.findById(id).orElse(null);
        if (post != null) {
            return new ResponseEntity<>(post, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post, @AuthenticationPrincipal OAuth2User user) {
        String userName = (String) user.getAttribute("name");
        String userImageUrl = (String) user.getAttribute("picture");
        String userEmail = (String) user.getAttribute("email");
        System.out.println("User email: " + userEmail);
        post.setUserName(userName);
        post.setUserImageUrl(userImageUrl);
        post.setUserEmailAddress(userEmail);
        Post savedPost = postRepository.save(post);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }

    @GetMapping("/like/{id}")
    public ResponseEntity<Post> likePost(@PathVariable String id, @AuthenticationPrincipal OAuth2User user) {
        Post post = postRepository.findById(id).orElse(null);
        if (post != null) {
            String userId = (String) user.getAttribute("email");
            post.getLikedBy().add(userId);
            post.setLikes(post.getLikes() + 1);
            postRepository.save(post);
            return new ResponseEntity<>(post, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/dislike/{id}")
    public ResponseEntity<Post> dislikePost(@PathVariable String id, @AuthenticationPrincipal OAuth2User user) {
        Post post = postRepository.findById(id).orElse(null);
        if (post != null) {
            String userId = (String) user.getAttribute("email");
            post.getLikedBy().remove(userId);
            post.setLikes(post.getLikes() - 1);
            postRepository.save(post);
            return new ResponseEntity<>(post, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable String id) {
        postRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> editPost(@PathVariable String id, @RequestBody Post post) {
        Post postToEdit = postRepository.findById(id).orElse(null);
        if (postToEdit != null) {
            postToEdit.setContent(post.getContent());
            postRepository.save(postToEdit);
            return new ResponseEntity<>(postToEdit, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
