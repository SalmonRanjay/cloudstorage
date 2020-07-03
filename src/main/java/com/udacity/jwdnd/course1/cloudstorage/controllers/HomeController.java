package com.udacity.jwdnd.course1.cloudstorage.controllers;

import java.lang.ProcessBuilder.Redirect;
import java.security.Principal;

import com.udacity.jwdnd.course1.cloudstorage.exception.FileExistException;
import com.udacity.jwdnd.course1.cloudstorage.exception.GenericException;
import com.udacity.jwdnd.course1.cloudstorage.exception.NullFileException;
import com.udacity.jwdnd.course1.cloudstorage.exception.UserNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.models.Files;
import com.udacity.jwdnd.course1.cloudstorage.models.Notes;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class HomeController {

    private FileService fileService;
    private NoteService noteService;

    public HomeController(FileService fileService, NoteService noteService) {
        this.fileService = fileService;
        this.noteService = noteService;
    }

    @GetMapping(value = "/home")
    public String showHome(Model model) {
        model.addAttribute("files", fileService.fetchAll());
        model.addAttribute("notes", noteService.getAllNotes());
        return "home";
    }

    @PostMapping(value = "/file-upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file, Principal principal) {

        fileService.save(file, principal);

        return "redirect:/home";
    }

    @GetMapping(value = "/view")
    public ResponseEntity<byte[]> viewFile(@RequestParam("filename") String filename) {
        HttpHeaders headers = new HttpHeaders();

       
        Files file = fileService.findByFilename(filename);

        headers.setContentType(MediaType.parseMediaType(file.getContenttype()));

        headers.add("content-disposition", "inline;filename=" + filename);

        //  for viewing file
        // headers.add("Content-Disposition", "inline; filename=" + "example.pdf");

        // For downloading file
        // headers.add("Content-Disposition", "attachment; filename=" + "example.pdf");

        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(file.getFiledata(), headers, HttpStatus.OK);
        return response;
    }

    @GetMapping(value="/delete")
    public String deleteFile(@RequestParam("filename") String filename, RedirectAttributes attributes) {
        
        fileService.delete(filename);
        return "redirect:/home";
    }

    @PostMapping(value="/note")
    public String createNote( @RequestParam("noteId") String noteId, @RequestParam("noteTitle") String title, @RequestParam("noteDescription") String description, Principal principal) { 
       
        if(noteId.equals("") || noteId == null){
            Notes note = new Notes(null, title, description, null);
            noteService.save(note, principal);
        }else{
            Notes note = new Notes(Integer.parseInt(noteId), title, description, null);
            noteService.updateNote(note);
        }
        return "redirect:/home";
    }
    
    @GetMapping(value="/delete-note")
    public String deleteNote(@RequestParam("id") String id) {
        noteService.deleteNote(Integer.parseInt(id));
        return "redirect:/home";
    }
    
    

    @ExceptionHandler({ FileExistException.class, UserNotFoundException.class, GenericException.class})
    public String handleExceptions(FileExistException ex, RedirectAttributes attributes) {
        attributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/home";
    }

    @ExceptionHandler({ NullFileException.class })
    public String handleNullFileException(FileExistException ex, RedirectAttributes attributes) {
        attributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/home";
    }

}