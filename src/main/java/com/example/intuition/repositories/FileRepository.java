package com.example.intuition.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.example.intuition.entities.Document;
import com.example.intuition.entities.File;
import com.example.intuition.entities.Folder;

@Repository
public class FileRepository {
    private static Map<Integer, Folder> userRoots = new HashMap<>();
    private static Map<Integer, File> files = new HashMap<>();
    private static int incrementalId = 0;

    public Folder createRoot(int userId) {
        if (userRoots.get(userId) != null) userRoots.get(userId);
        incrementalId++;
        Folder root = new Folder(incrementalId, "root", userId, null);
        root.init();
        userRoots.put(userId, root);
        files.put(incrementalId, root);
        return root;
    }

    public Folder getUserRoot(int userId) {
        return userRoots.get(userId);
    }

    public File createFile(String name, int userId, int parentId, String type) {
        incrementalId++;
        Folder parent = (Folder)files.get(parentId);
        File newFile = File.createFile(incrementalId, name, userId, parent, type);
        newFile.init();
        files.put(incrementalId, newFile);
        return newFile;
    }

    public File moveFile(int fileId, int oldParentId, int newParentId) {
        File f = files.get(fileId);
        Folder oldPa = (Folder)files.get(oldParentId);
        Folder newPa = (Folder)files.get(newParentId);
        oldPa.removeChild(fileId);
        newPa.addChild(f);
        return f;
    }

    public Document updateDocument(int docId, String content) {
        Document doc = (Document)files.get(docId);
        doc.setContent(content);
        return doc;
    }

    public File delete(int fileId) {
        File deletedFile = files.get(fileId);
        deletedFile.delete();
        files.remove(fileId);
        return deletedFile;
    }

    public File rename(int fileId, String newName) {
        File f = files.get(fileId);
        f.setName(newName);
        return f;
    }

    public List<Document> getAllUserTexts(int userId) {
        List<Document> docs = new ArrayList<>();
        for (File f : files.values()) {
            if (f.getType().equals("file") && f.getUserId() == userId) {
                docs.add((Document)f);
            }
        }
        return docs;
    }

    public File getFileById(int docId) {
        return files.get(docId);
    }
}
