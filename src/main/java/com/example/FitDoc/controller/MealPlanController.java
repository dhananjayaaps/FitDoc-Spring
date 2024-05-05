package com.example.FitDoc.controller;

import com.example.FitDoc.model.Comment;
import com.example.FitDoc.model.MealPlan;
import com.example.FitDoc.model.Post;
import com.example.FitDoc.model.User;
import com.example.FitDoc.repository.MealPlanRepository;
import com.example.FitDoc.repository.MealPlanRepository;
import com.example.FitDoc.repository.UserRepository;
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
import java.util.Map;

@RestController
@RequestMapping("/mealplan")
public class MealPlanController {


    @Autowired
    private MealPlanRepository MealPlanRepository;
    private final UserRepository userRepository;

    public MealPlanController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping()
    public String getAllPosts(@AuthenticationPrincipal OAuth2User user) {
        List<MealPlan> posts = MealPlanRepository.findAll();
        String userId = (String) user.getAttribute("email");

        JsonArray jsonArray = new JsonArray();

        for (MealPlan post : posts) {
            JsonObject postObject = new JsonObject();

            String Timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

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
            postObject.addProperty("commentsCount", post.getComments().size());

            // Construct JSON array for comments
            JsonArray commentsArray = new JsonArray();
            for (Comment comment : post.getComments()) {
                JsonObject commentObject = new JsonObject();
                commentObject.addProperty("id", comment.getId());
                commentObject.addProperty("content", comment.getContent());
                commentObject.addProperty("commenterName", comment.getCommenterName());
                commentObject.addProperty("commenterImageUrl", comment.getCommenterImageUrl());
                commentObject.addProperty("commenterEmailAddress", comment.getCommenterEmailAddress());
                commentObject.addProperty("timestamp", comment.getTimestamp());
                commentsArray.add(commentObject);
            }
            postObject.add("comments", commentsArray);

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
    public ResponseEntity<MealPlan> getPostById(@PathVariable String id) {
        MealPlan post = MealPlanRepository.findById(id).orElse(null);
        if (post != null) {
            return new ResponseEntity<>(post, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<MealPlan> createPost(@RequestBody MealPlan post, @AuthenticationPrincipal OAuth2User user) {
        String userName = (String) user.getAttribute("name");
        String userImageUrl = (String) user.getAttribute("picture");
        String userEmail = (String) user.getAttribute("email");
        String Timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        post.setUserName(userName);
        post.setUserImageUrl(userImageUrl);
        post.setUserEmailAddress(userEmail);
        post.setTimestamp(Timestamp);
        MealPlan savedPost = MealPlanRepository.save(post);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }

    @GetMapping("/like/{id}")
    public ResponseEntity<MealPlan> likePost(@PathVariable String id, @AuthenticationPrincipal OAuth2User user) {
        return getPostResponseEntity(id, user, MealPlanRepository);
    }

    ResponseEntity<MealPlan> getPostResponseEntity(@PathVariable String id, @AuthenticationPrincipal OAuth2User user, MealPlanRepository MealPlanRepository) {
        MealPlan post = MealPlanRepository.findById(id).orElse(null);
        if (post != null) {
            String userId = (String) user.getAttribute("email");

            post.getLikedBy().add(userId);
            post.setLikes(post.getLikes() + 1);
            MealPlanRepository.save(post);

            //create a notification for the post owner
            String name = (String) user.getAttribute("name");
            String postOwner = post.getUserEmailAddress();
            User gettingUser = userRepository.findByEmail(postOwner);
            String text = "Your post has been liked by " + name;
            gettingUser.addNotification(text);
            this.userRepository.save(gettingUser);

            return new ResponseEntity<>(post, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/dislike/{id}")
    public ResponseEntity<MealPlan> dislikePost(@PathVariable String id, @AuthenticationPrincipal OAuth2User user) {
        MealPlan post = MealPlanRepository.findById(id).orElse(null);
        if (post != null) {
            String userId = (String) user.getAttribute("email");
            post.getLikedBy().remove(userId);
            post.setLikes(post.getLikes() - 1);
            MealPlanRepository.save(post);
            return new ResponseEntity<>(post, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable String id) {
        MealPlanRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MealPlan> editPost(@PathVariable String id, @RequestBody MealPlan post) {
        MealPlan postToEdit = MealPlanRepository.findById(id).orElse(null);
        if (postToEdit != null) {
            postToEdit.setContent(post.getContent());
            MealPlanRepository.save(postToEdit);
            return new ResponseEntity<>(postToEdit, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<Comment> addCommentToPost(@PathVariable String postId, @RequestBody Map<String, String> commentData, @AuthenticationPrincipal OAuth2User user) {
        MealPlan post = MealPlanRepository.findById(postId).orElse(null);
        if (post == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String content = commentData.get("content");

        String commenterName = (String) user.getAttribute("name");
        String commenterImageUrl = (String) user.getAttribute("picture");
        String commenterEmailAddress = (String) user.getAttribute("email");
        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        Comment comment = new Comment(content);

        String id = Long.toHexString(Double.doubleToLongBits(Math.random()));
        comment.setId(id);
        comment.setCommenterName(commenterName);
        comment.setCommenterImageUrl(commenterImageUrl);
        comment.setCommenterEmailAddress(commenterEmailAddress);
        comment.setTimestamp(timestamp);

        post.getComments().add(comment);
        MealPlanRepository.save(post);

        String name = (String) user.getAttribute("name");
        String postOwner = post.getUserEmailAddress();
        User gettingUser = userRepository.findByEmail(postOwner);
        String text = "Your post has been commented by " + name;
        gettingUser.addNotification(text);
        userRepository.save(gettingUser);

        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }


    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<Void> deleteCommentFromPost(@PathVariable String postId, @PathVariable String commentId) {
        MealPlan post = MealPlanRepository.findById(postId).orElse(null);
        if (post == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        boolean removed = post.getComments().removeIf(comment -> comment.getId().equals(commentId));
        if (removed) {
            MealPlanRepository.save(post);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
