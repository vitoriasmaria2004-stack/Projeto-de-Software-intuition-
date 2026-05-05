package com.example.intuition.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.example.intuition.entities.Edge;
import com.example.intuition.entities.GraphDTO;
import com.example.intuition.entities.Vertice;

@Repository
public class GraphRepository {
    private static Map<Integer, Map<Integer, List<Edge>>> userGraphs = new HashMap<>();

    public void createGraph(int userId) {
        if (userGraphs.get(userId) == null) {
            userGraphs.put(userId, new HashMap<>());
        }
    }

    public void addEdge(int userId, int sourceId, int destinationId, String keyword) {
        Map<Integer, List<Edge>> graph = userGraphs.get(userId);
        Edge newEdge = new Edge(sourceId, destinationId, keyword);
        if (graph.get(sourceId) == null) {
            graph.put(sourceId, new ArrayList<>());
        }
        if (graph.get(destinationId) == null) {
            graph.put(destinationId, new ArrayList<>());
        }
        graph.get(sourceId).add(newEdge);
    }

    public GraphDTO getGraph(int userId) {
        Map<Integer, List<Edge>> graph = userGraphs.get(userId);
        List<Vertice> vrtxs = new ArrayList<>();
        for (int v : graph.keySet()) {
            vrtxs.add(new Vertice(v));
        }
        List<Edge> edges = new ArrayList<>();
        for (List<Edge> vEdges : graph.values()) {
            edges.addAll(vEdges);
        }
        return new GraphDTO(vrtxs, edges);
    }
}
