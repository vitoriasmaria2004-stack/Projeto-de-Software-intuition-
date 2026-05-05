package com.example.intuition.entities;

public abstract class File {
    protected int id;
    private String name;
    private int userId;
    protected String type;
    protected Folder parent;
    protected int parentId;

    public static File createFile(int id, String name, int userId, Folder parent, String type) {
        if (type.equals("file")) {
            return new Document(id, name, userId, parent);
        } else {
            return new Folder(id, name, userId, parent);
        }
    }

    public File(int id, String name, int userId, Folder parent) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.parent = parent;
        if (parent != null) {
            parent.addChild(this);
        }
        if (parent != null) {
            this.parentId = parent.id;
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getUserId() {
        return userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public abstract void init();
    public abstract void delete();
}
