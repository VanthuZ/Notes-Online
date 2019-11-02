package pl.vanthus.notesonline.gui;

import com.vaadin.flow.component.UI;
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
    private TextField titleField;
    private Checkbox isImportantCheckBox;
    private TextArea contentArea;
    private Button saveButton;
    private TextField noteId;

    @Autowired
    public NoteGui(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;



        initNoteLayout();
        initGridView();


    }


    private void initNoteLayout(){

        FormLayout noteLayout = new FormLayout();

        noteId = new TextField();
        noteId.setVisible(false);
        titleField = new TextField("Title");
        isImportantCheckBox = new Checkbox("Important");
        contentArea = new TextArea("Content");
        saveButton  = new Button("Save Note");

        saveButton.addClickListener(event -> {

            if(noteId.isEmpty()){

                noteService.saveNote(new Note(
                        titleField.getValue(),
                        contentArea.getValue(),
                        isImportantCheckBox.getValue(),
                        userService.getUserById(1L)
                ));

            }else{

                noteService.updateNote(
                        Long.parseLong(noteId.getValue()),
                        titleField.getValue(),
                        contentArea.getValue(),
                        isImportantCheckBox.getValue());
            }

            UI.getCurrent().getPage().reload();

        });


        noteLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("25em", 1));

        noteLayout.add(titleField, contentArea, isImportantCheckBox, saveButton);

        setHorizontalComponentAlignment(Alignment.CENTER, noteLayout);
        add(noteLayout);

    }



    private void initGridView(){


        Grid<Note> gridNote = new Grid<>(Note.class);
        gridNote.setItems(noteService.getAllUserNotes(1L));

        gridNote.removeColumnByKey("id");
        gridNote.removeColumnByKey("user");

        gridNote.setColumns("title", "content", "important", "createDate", "modifiedDate");

        add(gridNote);

        gridNote.addItemDoubleClickListener(event -> {
            titleField.setValue(event.getItem().getTitle());
            contentArea.setValue(event.getItem().getContent());
            isImportantCheckBox.setValue(event.getItem().isImportant());
            noteId.setValue(event.getItem().getId().toString());
        });

    }


}
