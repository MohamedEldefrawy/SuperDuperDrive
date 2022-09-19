package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {
    private static final String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

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

    public void downloadFile(HttpServletResponse response, File file) {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename = " + file.getFileName();
        response.setHeader(headerKey, headerValue);
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(file.getFileData());
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer saveFile(File file) {
        return this.fileMapper.insert(file);
    }

    public Integer deleteFile(Integer fileId) {
        return this.fileMapper.delete(fileId);
    }

    public File getFile(String fileName) {
        return this.fileMapper.select(fileName);
    }
}
