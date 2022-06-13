package com.example.strawberry.Model;

public class Image {
    private String linkImage;

    public Image() {
    }

    public Image(String linkImage) {
        this.linkImage = linkImage;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }

    @Override
    public String toString() {
        return "Image{" +
                "linkImage='" + linkImage + '\'' +
                '}';
    }
}
