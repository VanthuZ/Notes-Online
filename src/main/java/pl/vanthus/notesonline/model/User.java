package pl.vanthus.notesonline.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Email(message = "Invalid e-mail address")
    @NotBlank(message = "E-mail can't be null")
    private String email;
    @NotBlank(message = "Password can't be null")
    @Size(min = 6, message = "Password must have at least 6 characters")
    private String password;
    private LocalDateTime register_date = LocalDateTime.now();
    private boolean activity = true;
    @Transient
    @NotBlank(message = "Password can't be null")
    @Size(min = 6, message = "Password must have at least 6 characters")
    private String password_confirm;

}
