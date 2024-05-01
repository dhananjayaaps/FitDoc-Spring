package com.example.FitDoc.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "posts")
public class Post {

    @Id
    private String id;
    private String content;
    private String imageUrl;
    private String UserName;
    private String userImageUrl;
    private int likes;
    private String userEmailAddress;
    private List<String> likedBy;
    private List<Comment> comments;
    private String timestamp;

    public Post(String content, String imageUrl) {
        super();
        this.content = content;
        this.imageUrl = imageUrl;
        this.likes = 0;
        this.likedBy = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", likes=" + likes +
                '}';
    }

    public void setUserName(String userName) {
        this.UserName = userName;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setUserEmailAddress(String userEmailAddress) {
        this.userEmailAddress = userEmailAddress;
    }

    public List<String> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(List<String> likedBy) {
        this.likedBy = likedBy;
    }

    public String getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUserName() {
        return UserName;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public String getUserEmailAddress() {
        return userEmailAddress;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}

