package com.example.FitDoc.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "posts")
public class Post {

    @Id
    private String id;
    private String content;
    private String imageUrl;
    private int likes;

    public Post(String content, String imageUrl) {
        super();
        this.content = content;
        this.imageUrl = imageUrl;
        this.likes = 0;
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

}

