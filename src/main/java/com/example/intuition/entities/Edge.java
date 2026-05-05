package com.example.intuition.entities;

public class Edge {
    private int source;
    private int destination;
    private String keyword;

    public Edge(int source, int destination, String keyword) {
        this.source = source;
        this.destination = destination;
        this.keyword = keyword;
    }

    public int getDestination() {
        return destination;
    }

    public String getKeyword() {
        return keyword;
    }

    public int getSource() {
        return source;
    }
}
