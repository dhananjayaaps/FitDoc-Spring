package com.example.FitDoc.controller;

import com.example.FitDoc.model.Post;
import com.example.FitDoc.model.User;
import com.example.FitDoc.repository.PostRepository;
import com.example.FitDoc.repository.UserRepository;
import com.mongodb.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
public class UserController {

    @Autowired
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user/details")
    public Map<String, Object> userDetails(@AuthenticationPrincipal OAuth2User user) {
        return user.getAttributes();
    }

    @GetMapping("/authenticated")
    public ResponseEntity<?> userDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
        }
        try {
            OAuth2User user = (OAuth2User) authentication.getPrincipal();
            return ResponseEntity.ok("ok");
        } catch (ClassCastException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
        }
    }

    @GetMapping("/user/image")
    private String getProfilePictureUrl(String username, @AuthenticationPrincipal OAuth2User user) {
        return user.getAttribute("picture");
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity<User> getPostById(@PathVariable String username) {
        System.out.println("username: " + username);
        User profile = userRepository.findByUsername(username);
        System.out.println("profile: " + profile);
        if (profile != null) {
            return new ResponseEntity<>(profile, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/logout")
    public String logout() {
        return "redirect:http://localhost:3000/";
    }

    @PostMapping("/follow/{username}")
    public ResponseEntity<User> followUser(@PathVariable String username, @AuthenticationPrincipal OAuth2User user) {
        return getPostResponseEntity(username, user, userRepository);
    }

    @PostMapping("/unfollow/{username}")
    public ResponseEntity<User> unfollowUser(@PathVariable String username, @AuthenticationPrincipal OAuth2User user) {
        User profile = userRepository.findByUsername(username);
        if (profile != null) {
            String userId = (String) user.getAttribute("email");
            profile.getFollowedBy().remove(userId);
            profile.setFollowers(profile.getFollowers() - 1);
            userRepository.save(profile);
            return new ResponseEntity<>(profile, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    ResponseEntity<User> getPostResponseEntity(@PathVariable String id, @AuthenticationPrincipal OAuth2User user, UserRepository userRepository) {
        User account = userRepository.findByUsername(id);
        if (account != null) {
            String userId = (String) user.getAttribute("email");

            account.getFollowedBy().add(userId);
            account.setFollowers(account.getFollowers() + 1);
            userRepository.save(account);

            //create a notification for the post owner
            String name = (String) user.getAttribute("name");
            String postOwner = account.getEmail();
            User gettingUser = userRepository.findByEmail(postOwner);
            String text = "Your are followed by " + name;
            gettingUser.addNotification(text);
            this.userRepository.save(gettingUser);

            return new ResponseEntity<>(account, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/update/{username}")
    public ResponseEntity<User> updateProfile(@PathVariable String username, @AuthenticationPrincipal OAuth2User user, @RequestBody User userUpdate) {
        User profile = userRepository.findByUsername(username);
        if (profile != null) {
            String userId = (String) user.getAttribute("email");
            if(!userUpdate.getBio().isEmpty()){
                profile.setBio(userUpdate.getBio());
            }
            if(!userUpdate.getMobile().isEmpty()){
                profile.setMobile(userUpdate.getMobile());
            }
            userRepository.save(profile);
            return new ResponseEntity<>(profile, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/delete/{username}")
    public ResponseEntity<User> deleteProfile(@PathVariable String username, @AuthenticationPrincipal OAuth2User user) {
        User profile = userRepository.findByUsername(username);
        if (profile != null) {
            String userId = (String) user.getAttribute("email");
            userRepository.delete(profile);
            return new ResponseEntity<>(profile, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}

