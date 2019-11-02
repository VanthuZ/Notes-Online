package pl.vanthus.notesonline.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String content;
    private boolean isImportant;
    private LocalDateTime createDate = LocalDateTime.now();
    private LocalDateTime modifiedDate;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;


    public Note(String title, String content, boolean isImportant) {
        this.title = title;
        this.content = content;
        this.isImportant = isImportant;
    }

    public Note(String title, String content, boolean isImportant, User user) {
        this.title = title;
        this.content = content;
        this.isImportant = isImportant;
        this.user = user;
    }
}
