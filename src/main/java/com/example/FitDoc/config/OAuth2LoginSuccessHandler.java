package com.example.FitDoc.config;

import com.example.FitDoc.model.Post;
import com.example.FitDoc.model.User;
import com.example.FitDoc.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static java.lang.System.exit;

@Component
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserRepository userRepository;

    @Autowired
    public OAuth2LoginSuccessHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        this.setAlwaysUseDefaultTargetUrl(true);
        this.setDefaultTargetUrl("http://localhost:3000/");

        OAuth2User user = (OAuth2User) authentication.getPrincipal();

        String email = user.getAttribute("email");
        assert email != null;
        String username = email.substring(0, email.indexOf("@"));

        boolean userExists = userRepository.existsByUsername(username);

        if (!userExists) {
            String imageUrl = user.getAttribute("picture");
            String name = user.getAttribute("name");

            User newUser = new User(name, email, imageUrl, username);
            System.out.println(userRepository.save(newUser));
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
