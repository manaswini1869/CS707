package com.example.a707;

public class VideoFiles {
    private String id;
    private String path;
    private String title;
    private String size;
    private String duration;

    private String date;

    public VideoFiles(String id, String path, String title, String size, String duration, String date) {
        this.id = id;
        this.path = path;
        this.title = title;
        this.size = size;
        this.duration = duration;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
