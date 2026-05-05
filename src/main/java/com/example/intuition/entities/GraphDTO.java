package com.example.intuition.entities;

import java.util.List;

public class GraphDTO {
    private List<Vertice> vertices;
    private List<Edge> edges;

    public GraphDTO(List<Vertice> vertices, List<Edge> edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public List<Vertice> getVertices() {
        return vertices;
    }
}
