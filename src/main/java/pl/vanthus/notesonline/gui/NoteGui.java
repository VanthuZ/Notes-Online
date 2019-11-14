package pl.vanthus.notesonline.gui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import pl.vanthus.notesonline.model.Note;
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
    private Button removeButton;


    @Autowired
    public NoteGui(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;

        initLoginUserForm();
        initNoteLayout();
        initButtonLayout();
        initGridView();

    }


    private void initNoteLayout() {

        FormLayout noteLayout = new FormLayout();

        noteId = new TextField();
        noteId.setVisible(false);
        titleField = new TextField("Title");
        isImportantCheckBox = new Checkbox("Important");
        contentArea = new TextArea("Content");
        contentArea.setHeight("200px");

        noteLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("25em", 1));

        noteLayout.add(titleField, contentArea, isImportantCheckBox);

        add(noteLayout);
    }

    private void initButtonLayout() {

        FormLayout buttonLayout = new FormLayout();
        saveButton = new Button("Save Note");
        saveButton.addClickListener(event -> {

            if (noteId.isEmpty()) {

                noteService.saveNote(new Note(
                        titleField.getValue(),
                        contentArea.getValue(),
                        isImportantCheckBox.getValue(),
                        userService.getUserById(userService.setCurrentLoggedUserId())
                ));

            } else {

                noteService.updateNote(
                        Long.parseLong(noteId.getValue()),
                        titleField.getValue(),
                        contentArea.getValue(),
                        isImportantCheckBox.getValue());
            }

            UI.getCurrent().getPage().reload();

        });

        removeButton = new Button("Remove Note");
        removeButton.addClickListener(event -> {
            noteService.deleteNote(Long.parseLong(noteId.getValue()));
            UI.getCurrent().getPage().reload();
        });

        buttonLayout.add(saveButton, removeButton);
        add(buttonLayout);
    }


    private void initGridView() {

        Grid<Note> gridNote = new Grid<>(Note.class);
        gridNote.setItems(noteService.getAllNotesByUser(userService.setCurrentLoggedUserId()));

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

    private void initLoginUserForm() {

        FormLayout anchorLayout = new FormLayout();

        Label loggedUserLabel = new Label();
        loggedUserLabel.setText(userService.getCurrentUserName());
        loggedUserLabel.getStyle().set("margin-left", "80%");

        Anchor signIn = new Anchor("/login", "Sign in");
        Anchor logout = new Anchor("/logout", "Logout");
        Anchor signUp = new Anchor("/register", "Sign up");

        anchorLayout.add(signIn, logout, signUp);

        anchorLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("10em", 1),
                new FormLayout.ResponsiveStep("10em", 2),
                new FormLayout.ResponsiveStep("10em", 3));

        anchorLayout.getStyle().set("margin-left", "80%");

        if (userService.getCurrentUserName().equals("anonymousUser")) {
            signIn.setVisible(true);
            signUp.setVisible(true);
            logout.setVisible(false);
        } else {
            signIn.setVisible(false);
            signUp.setVisible(false);
            logout.setVisible(true);
        }
        add(loggedUserLabel, anchorLayout);
    }
}
