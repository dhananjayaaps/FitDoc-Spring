package com.example.FitDoc.controller;

import com.example.FitDoc.model.UserDetailsDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    private String getProfilePictureUrl(String username) {
        return "https://example.com/profiles/" + username + "/picture";
    }
}
