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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping()
    public String getAllPosts(@AuthenticationPrincipal OAuth2User user) {
        List<Post> posts = postRepository.findAll();
        String userId = (String) user.getAttribute("email");

        JsonArray jsonArray = new JsonArray();

        for (Post post : posts) {
            JsonObject postObject = new JsonObject();

            String Timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            System.out.println(Timestamp);
            System.out.println(post.getTimestamp().substring(0, 10));

            if (Timestamp.equals(post.getTimestamp().substring(0, 10))) {
                postObject.addProperty("timestamp", post.getTimestamp().substring(11, 16));
            } else {
                postObject.addProperty("timestamp", post.getTimestamp().substring(0, 10));
            }

            postObject.addProperty("id", post.getId());
            postObject.addProperty("content", post.getContent());
            postObject.addProperty("imageUrl", post.getImageUrl());
            postObject.addProperty("UserName", post.getUserName());
            postObject.addProperty("userImageUrl", post.getUserImageUrl());
            postObject.addProperty("likes", post.getLikes());
            postObject.addProperty("userEmailAddress", post.getUserEmailAddress());
            boolean liked = post.getLikedBy().contains(userId);
            postObject.addProperty("liked", liked);
            jsonArray.add(postObject);
        }
        Gson gson = new Gson();
        String jsonPosts = gson.toJson(jsonArray);
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
        String Timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        post.setUserName(userName);
        post.setUserImageUrl(userImageUrl);
        post.setUserEmailAddress(userEmail);
        post.setTimestamp(Timestamp);
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
