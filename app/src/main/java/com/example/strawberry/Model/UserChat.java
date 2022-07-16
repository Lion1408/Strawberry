package com.example.strawberry.Model;

public class UserChat {
    private String username, linkavt, status;

    public UserChat() {
    }

    public UserChat(String username, String linkavt, String status) {
        this.username = username;
        this.linkavt = linkavt;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLinkavt() {
        return linkavt;
    }

    public void setLinkavt(String linkavt) {
        this.linkavt = linkavt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserChat{" +
                "username='" + username + '\'' +
                ", linkavt='" + linkavt + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
