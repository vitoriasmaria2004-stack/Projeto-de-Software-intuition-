package com.example.intuition.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.intuition.entities.Document;
import com.example.intuition.entities.File;
import com.example.intuition.entities.Folder;
import com.example.intuition.repositories.FileRepository;

@RestController
public class FileController {
    
    private FileRepository fileRepository;

    FileController(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @PostMapping("file/document/create")
    @CrossOrigin(origins = "*")
    Document createDocument(@RequestBody Map<String,String> body) {
        String name = body.get("name");
        int userId = Integer.parseInt(body.get("userId"));
        int parentId = Integer.parseInt(body.get("parentId"));
        return (Document)fileRepository.createFile(name, userId, parentId, "file");
    }

    @PostMapping("file/folder/create")
    @CrossOrigin(origins = "*")
    Folder createFolder(@RequestBody Map<String,String> body) {
        String name = body.get("name");
        int userId = Integer.parseInt(body.get("userId"));
        int parentId = Integer.parseInt(body.get("parentId"));
        return (Folder)fileRepository.createFile(name, userId, parentId, "folder");
    }

    @PostMapping("file/move")
    @CrossOrigin(origins = "*")
    File moveFile(@RequestBody Map<String, String> body) {
        int fileId = Integer.parseInt(body.get("fileId"));
        int oldId = Integer.parseInt(body.get("oldId"));
        int newId = Integer.parseInt(body.get("newId"));
        return fileRepository.moveFile(fileId, oldId, newId);
    }

    @PostMapping("file/rename")
    @CrossOrigin(origins = "*")
    File renameFile(@RequestBody Map<String, String> body) {
        int fileId = Integer.parseInt(body.get("fileId"));
        String newName = body.get("newName");
        return fileRepository.rename(fileId, newName);
    }

    @GetMapping("file/delete")
    @CrossOrigin(origins = "*")
    File deleteFile(@RequestParam("fileId") int fileId) {
        return fileRepository.delete(fileId);
    }

    @PostMapping("file/document/save")
    @CrossOrigin(origins = "*")
    Document updateDocument(@RequestBody Map<String, String> body) {
        int docId = Integer.parseInt(body.get("docId"));
        String content = body.get("content");
        return fileRepository.updateDocument(docId, content);
    }

    @PostMapping("file/send")
    @CrossOrigin(origins = "*")
    File sendToUser(@RequestBody Map<String, String> body) {
        int userId = Integer.parseInt(body.get("userId"));
        int docId = Integer.parseInt(body.get("docId"));
        File doc = fileRepository.getFileById(docId);
        Folder newParentRoot = fileRepository.getUserRoot(userId);
        File newFile = fileRepository.createFile(doc.getName(), userId, newParentRoot.getId(), doc.getType());
        if (doc.getType().equals("file")) {
            String content = ((Document)doc).getContent();
            fileRepository.updateDocument(newFile.getId(), content);
        }
        return doc;
    }
}
