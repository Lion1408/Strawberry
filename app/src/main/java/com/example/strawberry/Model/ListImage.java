package com.example.strawberry.Model;

import java.io.File;

public class ListImage {
    private File fileImages;

    public ListImage() {
    }

    public ListImage(File fileImages) {
        this.fileImages = fileImages;
    }

    public File getFileImages() {
        return fileImages;
    }

    public void setFileImages(File fileImages) {
        this.fileImages = fileImages;
    }

    @Override
    public String toString() {
        return "ListImage{" +
                "fileImages=" + fileImages +
                '}';
    }
}
