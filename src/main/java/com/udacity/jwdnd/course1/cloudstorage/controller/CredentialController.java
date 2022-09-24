package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.util.FormValidation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
public class CredentialController {
    private final CredentialService credentialService;
    private final UserService userService;
    @Value("${SECRET_KEY}")
    private String key;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }


    @PostMapping("/credentials")
    public ModelAndView saveCredential(ModelMap model, @ModelAttribute("newCredential") Credential newCredential, @RequestParam("mode") Integer mode, Authentication authentication) {
        User selectedUser = this.userService.getUser(authentication.getName());
        newCredential.setUserId(selectedUser.getUserId());
        newCredential.setKey(this.key);

        var formValues = new ArrayList<String>();
        formValues.add(newCredential.getUrl());
        formValues.add(newCredential.getUserName());
        formValues.add(newCredential.getPassword());

        Integer result = 0;
        switch (mode) {
            case 0: {
                if (FormValidation.isValid(formValues))
                    result = this.credentialService.insert(newCredential);
                else
                    result = 0;
                break;
            }
            case 1: {
                if (FormValidation.isValid(formValues))
                    result = this.credentialService.edit(newCredential);
                else
                    result = 0;
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
    public String deleteCredential(@RequestParam("credentialId") Integer credentialId) {
        this.credentialService.delete(credentialId);
        return "redirect:/home";
    }
}
