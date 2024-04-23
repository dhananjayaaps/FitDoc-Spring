package com.example.FitDoc.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "posts")
public class User {
    private String id;
    private String name;
    private String email;
    private String imageUrl;
    private List<String> followedBy;
    private int followers;
    private String userImageUrl;

    public User(String name, String email, String imageUrl) {
        super();
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
        this.followers = 0;
        this.followedBy = null;
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

}
