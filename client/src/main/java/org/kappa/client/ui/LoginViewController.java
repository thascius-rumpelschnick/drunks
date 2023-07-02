package org.kappa.client.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginViewController {

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Button loginButton;

    @FXML
    private void initialize() {
        /*loginButton.setOnAction(event -> {
            String username = usernameTextField.getText();
            String password = passwordTextField.getText();

            System.out.println("Username: " + username);
            System.out.println("Password: " + password);
        });*/
    }
}
