package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {
    private UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String home(Model model, @CurrentSecurityContext(expression = "authentication?.name")
    String userName, @RequestParam Optional<Boolean> error, @RequestParam Optional<String> msg) {


        List<File> userFiles = this.userService.getUserFiles(userName);
        model.addAttribute("files", userFiles);
        if (error.isPresent() || msg.isPresent()) {
            model.addAttribute("msg", msg.get());
            model.addAttribute("error", error.get());
        }
        return "home";
    }
}
