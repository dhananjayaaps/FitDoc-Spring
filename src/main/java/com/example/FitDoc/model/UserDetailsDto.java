package com.example.FitDoc.model;

public class UserDetailsDto {
    private String username;
    private String profilePictureUrl;

    public UserDetailsDto() {
    }

    public UserDetailsDto(String username, String profilePictureUrl) {
        this.username = username;
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public void setProfilePicture(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }
}
