package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    private UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String home(Model model, @CurrentSecurityContext(expression = "authentication?.name")
    String userName) {


        List<File> userFiles = this.userService.getUserFiles(userName);
        model.addAttribute("files", userFiles);
        return "home";
    }
}
