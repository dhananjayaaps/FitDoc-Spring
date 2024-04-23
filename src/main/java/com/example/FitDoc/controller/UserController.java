package com.example.FitDoc.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("/user/image")
    private String getProfilePictureUrl(String username, @AuthenticationPrincipal OAuth2User user) {
        return user.getAttribute("picture");
    }
}
