package pl.vanthus.notesonline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.vanthus.notesonline.model.Note;

@Repository
public interface NoteRepository extends JpaRepository<Long, Note> {
}
