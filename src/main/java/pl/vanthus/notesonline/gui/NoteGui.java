package pl.vanthus.notesonline.gui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.vanthus.notesonline.model.Note;
import pl.vanthus.notesonline.model.User;
import pl.vanthus.notesonline.service.NoteService;
import pl.vanthus.notesonline.service.UserService;


@Route("notes")
public class NoteGui extends VerticalLayout {

    private NoteService noteService;
    private UserService userService;

    @Autowired
    public NoteGui(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;



        initNoteLayout();


    }


    private void initNoteLayout(){

        FormLayout noteLayout = new FormLayout();


        TextField titleField = new TextField("Title");
        Checkbox isImportantCheckBox = new Checkbox("Important");
        TextArea contentArea = new TextArea("Content");
        Button saveButton  = new Button("Save Note");

        saveButton.addClickListener(event -> noteService.saveNote(new Note(
                titleField.getValue(),
                contentArea.getValue(),
                isImportantCheckBox.getValue(),
                userService.getUserById(1L)
        )));


        noteLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("25em", 1));

        noteLayout.add(titleField, contentArea, isImportantCheckBox, saveButton);

        setHorizontalComponentAlignment(Alignment.CENTER, noteLayout);
        add(noteLayout);

    }
}
