package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class FileController {

    private final FileService fileService;
    private final UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/upload")
    public String upload(Model model, @RequestParam("fileUpload") MultipartFile file, @CurrentSecurityContext(expression = "authentication?.name")
    String userName) {
        StringBuilder names = this.fileService.uploadFile(file);
        model.addAttribute("msg", "Uploaded files: " + names.toString());
        byte[] fileData;
        try {
            fileData = file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        User selectedUser = this.userService.getUser(userName);
        this.fileService.saveFile(new File(file.getName(), file.getContentType(), String.valueOf(file.getSize()), selectedUser, fileData));
        return "home";
    }

    @GetMapping("/download")
    public String download(Model model, @RequestParam("fileName") String fileName, HttpServletResponse response) {
        File selectedFile = this.fileService.getFile(fileName);
        this.fileService.downloadFile(response, selectedFile);
        return "home";
    }

    @DeleteMapping("/delete")
    public String delete(Model model, @RequestParam("fileName") String fileName) {
        File selectedFile = this.fileService.getFile(fileName);
        this.fileService.deleteFile(selectedFile.getFileId());
        return "home";
    }
}
