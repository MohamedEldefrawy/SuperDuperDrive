package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public Integer insert(Note note) {
        return this.noteMapper.insert(note);
    }

    public List<Note> selectNotes(Integer userId) {
        return this.noteMapper.selectNotes(userId);
    }

    public Integer updateNote(Note newNote) {
        return this.noteMapper.edit(newNote);
    }

    public Integer delete(Integer noteId) {
        return this.noteMapper.delete(noteId);
    }
}
