package com.example.FitDoc.controller;

import com.example.FitDoc.model.User;
import com.example.FitDoc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class NotificationController {

    private final UserRepository userRepository;

    @Autowired
    public NotificationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/notifications")
    private List<String> getNotifications(@AuthenticationPrincipal OAuth2User oauth2User) {
        String email = oauth2User.getAttribute("email");
        if (email == null) {
            return null;
        }

        String username = email.substring(0, email.indexOf("@"));

        User user = userRepository.findByUsername(username);

        if (user != null) {
            List<String> notifications = user.getNotifications();
            // Sort notifications in descending order
            notifications.sort(Collections.reverseOrder());
            notifications.sort(Collections.reverseOrder());
            return notifications;
        } else {
            return null;
        }
    }


    //function for adding notifications
    @GetMapping("/addNotification")
    private String addNotification(@AuthenticationPrincipal OAuth2User oauth2User, String notification) {
        String email = oauth2User.getAttribute("email");
        if (email == null) {
            return "User not authenticated";
        }

        String username = email.substring(0, email.indexOf("@"));

        User user = userRepository.findByUsername(username);

        if (user != null) {
            user.addNotification(notification);
            userRepository.save(user);
            return "Notification added";
        } else {
            return "User not found";
        }
    }

}