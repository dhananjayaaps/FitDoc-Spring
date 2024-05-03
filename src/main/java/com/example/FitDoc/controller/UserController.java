package com.example.FitDoc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {

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

    @GetMapping("/logout")
    public String logout() {
        return "redirect:http://localhost:3000/";
    }

}

