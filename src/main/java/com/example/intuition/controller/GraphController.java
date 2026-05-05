package com.example.intuition.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.crew102.rapidrake.RakeAlgorithm;
import io.github.crew102.rapidrake.data.SmartWords;
import io.github.crew102.rapidrake.model.RakeParams;
import io.github.crew102.rapidrake.model.Result;

import com.example.intuition.entities.Document;
import com.example.intuition.entities.GraphDTO;
import com.example.intuition.entities.Vertice;
import com.example.intuition.repositories.FileRepository;
import com.example.intuition.repositories.GraphRepository;

@RestController
public class GraphController {
    private GraphRepository graphRepository;
    private FileRepository fileRepository;

    GraphController(GraphRepository graphRepository, FileRepository fileRepository) {
        this.graphRepository = graphRepository;
        this.fileRepository = fileRepository;
    }

    @GetMapping("graph")
    @CrossOrigin("*")
    GraphDTO getGraph(@RequestParam("userId") int userId) {
        GraphDTO graph = graphRepository.getGraph(userId);
        for (Vertice v : graph.getVertices()) {
            v.setName(fileRepository.getFileById(v.getId()).getName());
        }
        return graph;
    }

    @PostMapping("graph/edge")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin("*")
    void createEdge(@RequestBody Map<String, String> body) {
        int userId = Integer.parseInt(body.get("userId"));
        int sourceId = Integer.parseInt(body.get("sourceId"));
        int destinationId = Integer.parseInt(body.get("destinationId"));
        String keyword = body.get("keyword");
        graphRepository.addEdge(userId, sourceId, destinationId, keyword);
    }

    List<String> extractKeywords(Document text){
        String content = text.getContent();

        String[] stopWords = new SmartWords().getSmartWords();
        String[] stopPOS = {"VB", "VBD", "VBG", "VBN", "VBP", "VBZ"};
        int minWordChar = 1;
        boolean shouldStem = true;
        String phraseDelims = "[-,.?():;\"!/]";
        RakeParams params = new RakeParams(stopWords, stopPOS, minWordChar, shouldStem, phraseDelims);

        String POStaggerURL = "/home/nicolas/projects/intuition/intuition/src/main/resources/models/pt-pos-maxent.bin";
        String SentDetectURL = "/home/nicolas/projects/intuition/intuition/src/main/resources/models/pt-sent.bin";
        try {
            RakeAlgorithm rakeAlg = new RakeAlgorithm(params, POStaggerURL, SentDetectURL);
            Result res = rakeAlg.rake(content);
            return Arrays.asList(res.distinct().getFullKeywords());
        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
    }

    @GetMapping("graph/generate")
    @CrossOrigin("*")
    GraphDTO createGraph(@RequestParam("userId") int userId) {
        List<List<String>> keywords = new ArrayList<>();
        List<Document> texts = fileRepository.getAllUserTexts(userId);
        for (Document t : texts) {
            keywords.add(extractKeywords(t));
        }

        for (int i = 0; i < keywords.size(); i++) {
            for (int j = i+1; j < keywords.size(); j++) {
                List<String> kw1 = keywords.get(i);
                List<String> intersec = new ArrayList<>(keywords.get(j));
                intersec.retainAll(kw1);
                if (intersec.size() == 0) continue;

                int srcId = texts.get(i).getId();
                int destId = texts.get(j).getId();
                graphRepository.addEdge(userId, srcId, destId, intersec.get(0));
            }
        }
        GraphDTO graph = graphRepository.getGraph(userId);
        for (Vertice v : graph.getVertices()) {
            v.setName(fileRepository.getFileById(v.getId()).getName());
        }
        return graph;
    }
}
