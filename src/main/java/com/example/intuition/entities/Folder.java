package com.example.intuition.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Folder extends File {
    private List<File> children;

    public Folder(int id, String name, int userId, Folder parent) {
        super(id, name, userId, parent);
        this.type = "folder";
    }

    @Override
    public void init() {
        this.children = new ArrayList<>();
    }

    @Override
    public void delete() {
        if (children.size() > 0) {
            throw new RuntimeException("The folder must be empty to be deleted");
        }
        parent.removeChild(id);
    }

    public void addChild(File newFile) {
        children.add(newFile);
    }

    public void removeChild(int childId) {
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i).getId() == childId) {
                children.remove(i);
                return;
            }
        }
    }

    public List<File> getChildren() {
        return Collections.unmodifiableList(children);
    }
}
