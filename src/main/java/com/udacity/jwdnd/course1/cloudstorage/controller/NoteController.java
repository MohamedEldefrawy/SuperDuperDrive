package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping("/notes")
    public ModelAndView saveNote(ModelMap model, @ModelAttribute("newNote") Note newNote, @RequestParam("mode") Integer mode, Authentication authentication) {
        User selectedUser = this.userService.getUser(authentication.getName());
        newNote.setUserId(selectedUser.getUserId());
        Integer result = 0;
        switch (mode) {
            case 0: {
                result = this.noteService.insert(newNote);
                break;
            }
            case 1: {
                result = this.noteService.updateNote(newNote);
                break;
            }
        }

        if (result > 0) {
            model.addAttribute("msg", newNote.getTitle() + " note been created");
            model.addAttribute("error", false);

        } else {
            model.addAttribute("msg", "Cannot add note");
            model.addAttribute("error", true);
        }
        return new ModelAndView("redirect:/home", model);
    }

    @GetMapping("/notes/delete")
    public String deleteNote(Model model, @RequestParam("noteId") Integer noteId) {
        this.noteService.delete(noteId);
        return "redirect:/home";
    }
}
