package controllers;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import models.LoginModel;

public class LoginController {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblError;

    private LoginModel model;

    public LoginController() {
        model = new LoginModel();
    }

    public void login() {

        lblError.setText("");
        String username = this.txtUsername.getText();
        String password = this.txtPassword.getText();

        // Validations
        if (username == null || username.trim().equals("")) {
            lblError.setText("Username Cannot be empty or spaces");
            return;
        }
        if (password == null || password.trim().equals("")) {
            lblError.setText("Password Cannot be empty or spaces");
            return;
        }
        if (username == null || username.trim().equals("") && (password == null || password.trim().equals(""))) {
            lblError.setText("User name / Password Cannot be empty or spaces");
            return;
        }

        // authentication check
        checkCredentials(username, password);

    }

    public void checkCredentials(String username, String password) {
        Boolean isValid = model.getCredentials(username, password);
        if (!isValid) {
            lblError.setText("User does not exist!");
            return;
        }
        try {
            AnchorPane root;
            if (model.isAdmin() && isValid) {
                // If user is admin, inflate admin view
                root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/AdminView.fxml"));
                Main.stage.setTitle("Product Stock Manage System - Admin View - JiaYu Wang");
            } else {
                // If user is customer, inflate customer view
                root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/ClientView.fxml"));
                // ***Set user ID acquired from db****
                int user_id = 1; // hard coded for testing purposes only!!
                // ClientController.setUser(user_id);
                Main.stage.setTitle("Product Stock Manage System - Client View - JiaYu Wang");
            }
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/assets/main.css").toExternalForm());
            Main.stage.setScene(scene);
        } catch (Exception e) {
            System.out.println("Error occured while inflating view: " + e);
        }

    }
}
