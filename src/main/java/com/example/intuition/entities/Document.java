package com.example.intuition.entities;

public class Document extends File {
    private String content;

    public Document(int id, String name, int userId, Folder parent) {
        super(id, name, userId, parent);
        this.type = "file";
    }

    @Override
    public void init() {
        this.content = "";
    }

    @Override
    public void delete() {
        parent.removeChild(id);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
