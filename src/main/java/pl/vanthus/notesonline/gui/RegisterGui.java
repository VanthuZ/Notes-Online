package pl.vanthus.notesonline.gui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

import org.hibernate.HibernateException;

import org.springframework.beans.factory.annotation.Autowired;

import pl.vanthus.notesonline.model.User;
import pl.vanthus.notesonline.service.UserService;

@Route("register")
public class RegisterGui extends VerticalLayout {


    private UserService userService;

    @Autowired
    public RegisterGui(UserService userService) {
        this.userService = userService;

        FormLayout registerLayout = new FormLayout();

        Label signUpLabel = new Label("Sign up");
        EmailField emailField = new EmailField("Email");

        PasswordField passwordField = new PasswordField();
        passwordField.setLabel("Password");
        passwordField.setMinLength(6);

        PasswordField passwordConfirmField = new PasswordField();
        passwordConfirmField.setLabel("Confirm Password");
        passwordConfirmField.setMinLength(6);

        Button registerButton = new Button("Sign Up");

        Dialog dialog = new Dialog();


        registerButton.addClickListener(event -> {

            //todo add validation for fields

            if (!emailField.getValue().equals("") & userService.passwordCheck(passwordField.getValue(), passwordConfirmField.getValue())) {

                try {
                    userService.registerUser(new User(emailField.getValue(), passwordField.getValue()));
                    dialog.removeAll();
                    dialog.add(new Label("The user has been created"));
                    dialog.open();
                    UI.getCurrent().navigate("notes");

                } catch (HibernateException exception) {
                    dialog.removeAll();
                    dialog.add(new Label("Something went wrong :("));
                    dialog.open();
                }

            } else {
                dialog.removeAll();
                dialog.add(new Label("Check your email and passwords and try again"));
                dialog.open();
            }
        });
        registerLayout.add(signUpLabel, emailField, passwordField, passwordConfirmField, registerButton);
        setHorizontalComponentAlignment(Alignment.CENTER, registerLayout);
        registerLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("10em", 1));

        add(registerLayout);
    }
}
