package com.example.FitDoc.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "posts")
public class Post {

    @Id
    private String id;
    private String content;
    private String imageUrl;
    private String userName;
    private String userImageUrl;
    private int likes;
    private String userEmailAddress;

    public Post(String content, String imageUrl, String UserName) {
        super();
        this.content = content;
        this.imageUrl = imageUrl;
        this.likes = 0;
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
        this.userName = userName;
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

}

