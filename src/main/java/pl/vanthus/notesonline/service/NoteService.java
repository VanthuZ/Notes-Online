package pl.vanthus.notesonline.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.vanthus.notesonline.model.Note;
import pl.vanthus.notesonline.repository.NoteRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class NoteService {

    private NoteRepository noteRepository;


    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public void saveNote(Note note) {
        noteRepository.save(note);
    }

    public List<Note> getAllNotesByUser(Long userId) {
        return noteRepository.findAllByUserId(userId);
    }

    public void updateNote(Long noteId, String newTitle, String newContent, boolean isImportant) {

        Note noteToUpdate = noteRepository.getOne(noteId);

        noteToUpdate.setTitle(newTitle);
        noteToUpdate.setContent(newContent);
        noteToUpdate.setImportant(isImportant);
        noteToUpdate.setModifiedDate(LocalDateTime.now());

        noteRepository.save(noteToUpdate);

    }

    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }
}
