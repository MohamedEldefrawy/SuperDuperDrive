package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {
    private final UserService userService;
    private final NoteService noteService;

    private final FileService fileService;
    private final CredentialService credentialService;

    public HomeController(UserService userService, NoteService noteService, FileService fileService, CredentialService credentialService) {
        this.userService = userService;
        this.noteService = noteService;
        this.fileService = fileService;
        this.credentialService = credentialService;
    }

    @GetMapping("/home")
    public String home(Model model, @ModelAttribute("newNote") Note newNote, @ModelAttribute("newCredential") Credential newCredential, Authentication authentication, @RequestParam Optional<Boolean> error, @RequestParam Optional<String> msg) {
        var selectedUser = this.userService.getUser(authentication.getName());

        List<File> userFiles = this.fileService.selectUserFiles(selectedUser.getUserId());
        List<Note> userNotes = this.noteService.selectUserNotes(selectedUser.getUserId());
        List<Credential> userCredentials = this.credentialService.selectUserCredentials(selectedUser.getUserId());

        model.addAttribute("notes", userNotes);
        model.addAttribute("files", userFiles);
        model.addAttribute("credentials", userCredentials);

        if (error.isPresent() || msg.isPresent()) {
            model.addAttribute("msg", msg.get());
            model.addAttribute("error", error.get());
        }
        return "home";
    }
}
