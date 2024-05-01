package com.example.FitDoc.model;

import org.springframework.data.annotation.Id;

public class Comment {

    @Id
    private String id;
    private String content;
    private String commenterName;
    private String commenterImageUrl;
    private String commenterEmailAddress;
    private String timestamp;

    public Comment(String content) {
        this.content = content;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCommenterName() {
        return commenterName;
    }

    public void setCommenterName(String commenterName) {
        this.commenterName = commenterName;
    }

    public String getCommenterImageUrl() {
        return commenterImageUrl;
    }

    public void setCommenterImageUrl(String commenterImageUrl) {
        this.commenterImageUrl = commenterImageUrl;
    }

    public String getCommenterEmailAddress() {
        return commenterEmailAddress;
    }

    public void setCommenterEmailAddress(String commenterEmailAddress) {
        this.commenterEmailAddress = commenterEmailAddress;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
