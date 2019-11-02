package pl.vanthus.notesonline.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.vanthus.notesonline.model.Note;
import pl.vanthus.notesonline.model.User;
import pl.vanthus.notesonline.repository.NoteRepository;

import java.util.List;

@Service
public class NoteService{

    NoteRepository noteRepository;


    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public void saveNote(Note note){
        noteRepository.save(note);
    }

    public List<Note> getAllUserNotes(Long userId){
        return noteRepository.findAllByUserId(userId);
    }
}
