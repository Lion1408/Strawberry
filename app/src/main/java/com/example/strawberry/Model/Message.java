package com.example.strawberry.Model;

public class Message {
    private String content, time;
    private Integer itemtype;

    public Message() {
    }

    public Message(String content, String time, Integer itemtype) {
        this.content = content;
        this.time = time;
        this.itemtype = itemtype;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getItemtype() {
        return itemtype;
    }

    public void setItemtype(Integer itemtype) {
        this.itemtype = itemtype;
    }

    @Override
    public String toString() {
        return "Message{" +
                "content='" + content + '\'' +
                ", time='" + time + '\'' +
                ", itemtype=" + itemtype +
                '}';
    }
}
