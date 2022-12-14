package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView upload(ModelMap model, @RequestParam("fileUpload") MultipartFile file, @CurrentSecurityContext(expression = "authentication?.name")
    String userName) {

        if (!file.isEmpty()) {
            File selectedFile = this.fileService.getFile(file.getOriginalFilename());
            if (selectedFile == null) {
                StringBuilder names = this.fileService.uploadFile(file);
                model.addAttribute("msg", "Uploaded files: " + names.toString());
                model.addAttribute("error", false);
                byte[] fileData;
                try {
                    fileData = file.getBytes();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                User selectedUser = this.userService.getUser(userName);
                Integer result = this.fileService.saveFile(new File(file.getOriginalFilename(), file.getContentType(), String.valueOf(file.getSize()), selectedUser.getUserId(), fileData));
                System.out.println(result + " files inserted");
            } else {
                model.addAttribute("msg", "Cannot upload file twice");
                model.addAttribute("error", true);
            }
        }

        return new ModelAndView("redirect:/home", model);
    }

    @GetMapping("/download")
    public void download(Model model, @RequestParam("fileName") String fileName, HttpServletResponse response) {
        File selectedFile = this.fileService.getFile(fileName);
        this.fileService.downloadFile(response, selectedFile);
    }

    @GetMapping("/delete")
    public String delete(Model model, @RequestParam("fileName") String fileName) {
        File selectedFile = this.fileService.getFile(fileName);
        Integer result = this.fileService.deleteFile(selectedFile.getFileId());
        System.out.println(result + " files deleted");
        return "redirect:home";
    }
}
