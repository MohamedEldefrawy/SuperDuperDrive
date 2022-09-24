package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
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
public class CredentialController {
    private final CredentialService credentialService;
    private final UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }


    @PostMapping("/credentials")
    public ModelAndView saveCredential(ModelMap model, @ModelAttribute("newCredential") Credential newCredential, @RequestParam("mode") Integer mode, Authentication authentication) {
        User selectedUser = this.userService.getUser(authentication.getName());
        newCredential.setUserId(selectedUser.getUserId());
        newCredential.setKey("p@s$w0rd");
        Integer result = 0;
        switch (mode) {
            case 0: {
                result = this.credentialService.insert(newCredential);
                break;
            }
            case 1: {
                result = this.credentialService.edit(newCredential);
                break;
            }
        }
        if (result > 0) {
            model.addAttribute("msg", newCredential.getUrl() + " credential been created");
            model.addAttribute("error", false);

        } else {
            model.addAttribute("msg", "Cannot create credential");
            model.addAttribute("error", true);
        }
        return new ModelAndView("redirect:/home", model);

    }

    @GetMapping("/credentials/delete")
    public String deleteCredential(Model model, @RequestParam("credentialId") Integer credentialId) {
        this.credentialService.delete(credentialId);
        return "redirect:/home";
    }
}
