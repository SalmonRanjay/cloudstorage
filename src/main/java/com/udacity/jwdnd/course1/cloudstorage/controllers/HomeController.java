package com.udacity.jwdnd.course1.cloudstorage.controllers;

import java.security.Principal;

import com.udacity.jwdnd.course1.cloudstorage.exception.FileExistException;
import com.udacity.jwdnd.course1.cloudstorage.exception.NullFileException;
import com.udacity.jwdnd.course1.cloudstorage.exception.UserNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class HomeController {

    private FileService fileService;

    public HomeController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping(value = "/home")
    public String showHome(Model model) {
        model.addAttribute("files", fileService.fetchAll());
        return "home";
    }

    @PostMapping(value = "/file-upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file, Principal principal)  {

        

        fileService.save(file, principal);

        return "redirect:/home";
    }

    @ExceptionHandler({ FileExistException.class , UserNotFoundException.class, NullFileException.class})
    public String getSuperheroesUnavailable(FileExistException ex, RedirectAttributes attributes) {
        attributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/home";
    }

}