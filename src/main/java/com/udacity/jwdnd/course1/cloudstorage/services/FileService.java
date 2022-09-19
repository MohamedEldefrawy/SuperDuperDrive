package com.udacity.jwdnd.course1.cloudstorage.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {
    private static final String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";

    public StringBuilder uploadFile(MultipartFile file) {
        StringBuilder fileNames = new StringBuilder();
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
        fileNames.append(file.getOriginalFilename());
        try {
            Files.write(fileNameAndPath, file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileNames;
    }

    public void downloadFile() {

    }

    public Integer saveFile() {
        return 0;
    }

    public Integer deleteFile() {
        return 0;
    }
}
