package com.udacity.jwdnd.course1.cloudstorage.services;

import java.security.Principal;
import java.util.List;

import com.udacity.jwdnd.course1.cloudstorage.exception.GenericException;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Notes;
import com.udacity.jwdnd.course1.cloudstorage.models.User;

import org.springframework.stereotype.Service;

@Service
public class NoteService {

    private NotesMapper notesMapper;
    private UserMapper userMapper;

    public NoteService(NotesMapper notesMapper, UserMapper userMapper){
        this.notesMapper = notesMapper;
        this.userMapper = userMapper;
    }

    public List<Notes> getAllNotes(){
        return notesMapper.getNotes();
    }

    public void save(Notes note, Principal principal){
        User user = userMapper.getUser(principal.getName());
        if(user == null)
            throw new GenericException("Unable to Find user information for user:  " + user.getUsername());
        int result = notesMapper.insert(new Notes(null, note.getNoteTitle(), note.getNoteDescription(),
                user.getUserId()));

        if (result != 1)
            throw new GenericException("There was an issue saving note. Unable to Save");
    }

    public Notes getNotes(Integer noteId) {
        Notes note = notesMapper.getNoteById(noteId);
        if (note == null)
            throw new GenericException("Unable to Retrieve Note with Id: " + noteId);

        return note;
    }

    public void deleteNote(Integer id) {
        int result = notesMapper.delete(id);
        if (result != 1)
            throw new GenericException("Unable to Delete note with id: " + id);
    }

    public void updateNote(Notes note) {
        Notes dbNotes = notesMapper.getNoteById(note.getNoteId());
        if (dbNotes == null)
            throw new GenericException("Unable to retrieve note with id: " + note.getNoteId());

        int result = notesMapper.updateNote(note.getNoteId(), note.getNoteTitle(), note.getNoteDescription());

        if (result != 1)
            throw new GenericException("Unable to Update Note: " + note.getNoteId());
    }
    
}