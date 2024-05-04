package com.example.FitDoc.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String name;
    private String email;
    private String username;
    private String imageUrl;
    private List<String> followedBy;
    private List<String> notifications;
    private int followers;
    private String userImageUrl;
    private String bio;
    private String mobile;

    public User(String name, String email, String imageUrl, String username) {
        super();
        this.name = name;
        this.username = username;
        this.email = email;
        this.imageUrl = imageUrl;
        this.followers = 0;
        this.followedBy = new ArrayList<>();
        this.notifications = new ArrayList<>();
        this.bio = "Welcome to the FitDoc community!";
        this.mobile = "Not Set";
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getFollowers() {
        return followers;
    }

    public List<String> getFollowedBy() {
        return followedBy;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public void setFollowedBy(List<String> followedBy) {
        this.followedBy = followedBy;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getNotifications() {
        return notifications;
    }

    public void addNotification(String notification) {
        notifications.add(notification);
    }

    public String getBio() {return bio;}
    public void setBio(String bio) {this.bio = bio;}
    public String getMobile() {return mobile;}
    public void setMobile(String mobile) {this.mobile = mobile;}
}
