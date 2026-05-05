package com.example.intuition.entities;

public class Vertice {
    private int id;
    private String name;

    public Vertice(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
