package com.example.jfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class HelloApplication extends Application {

    private Scene homeScene, loginScene, createAccountScene;
    private Map<String, String> userDatabase = new HashMap<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Welcome to the System");

        // Home Scene
        Label welcomeLabel = new Label("Welcome to the System");
        Button loginButton = new Button("Login");
        Button createAccountButton = new Button("Create Account");

        loginButton.setOnAction(e -> primaryStage.setScene(loginScene));
        createAccountButton.setOnAction(e -> primaryStage.setScene(createAccountScene));

        VBox homeLayout = new VBox(10);
        homeLayout.getChildren().addAll(welcomeLabel, loginButton, createAccountButton);
        homeScene = new Scene(homeLayout, 300, 200);

        // Login Scene
        GridPane loginLayout = new GridPane();
        loginLayout.setVgap(10);
        loginLayout.setHgap(10);
        Label loginLabel = new Label("Login");
        Label usernameLabel = new Label("Username:");
        Label passwordLabel = new Label("Password:");
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button loginSubmitButton = new Button("Login");
        Button backButton1 = new Button("Back");

        backButton1.setOnAction(e -> primaryStage.setScene(homeScene));
        loginSubmitButton.setOnAction(e -> {
            if (validateUser(usernameField.getText(), passwordField.getText())) {
                showAlert(AlertType.INFORMATION, "Login Successful", "Welcome, " + usernameField.getText() + "!");
            } else {
                showAlert(AlertType.ERROR, "Login Failed", "Invalid username or password.");
            }
        });

        loginLayout.add(loginLabel, 0, 0);
        loginLayout.add(usernameLabel, 0, 1);
        loginLayout.add(usernameField, 1, 1);
        loginLayout.add(passwordLabel, 0, 2);
        loginLayout.add(passwordField, 1, 2);
        loginLayout.add(loginSubmitButton, 1, 3);
        loginLayout.add(backButton1, 0, 3);
        loginScene = new Scene(loginLayout, 300, 200);

        // Create Account Scene
        GridPane createAccountLayout = new GridPane();
        createAccountLayout.setVgap(10);
        createAccountLayout.setHgap(10);
        Label createAccountLabel = new Label("Create Account");
        Label newUsernameLabel = new Label("Username:");
        Label newPasswordLabel = new Label("Password:");
        Label confirmPasswordLabel = new Label("Confirm Password:");
        TextField newUsernameField = new TextField();
        PasswordField newPasswordField = new PasswordField();
        PasswordField confirmPasswordField = new PasswordField();
        Button createAccountSubmitButton = new Button("Create Account");
        Button backButton2 = new Button("Back");

        backButton2.setOnAction(e -> primaryStage.setScene(homeScene));
        createAccountSubmitButton.setOnAction(e -> {
            if (!newPasswordField.getText().equals(confirmPasswordField.getText())) {
                showAlert(AlertType.ERROR, "Signup Failed", "Passwords do not match.");
                return;
            }
            if (createUser(newUsernameField.getText(), newPasswordField.getText())) {
                showAlert(AlertType.INFORMATION, "Signup Successful", "Account created successfully.");
                primaryStage.setScene(homeScene);
            } else {
                showAlert(AlertType.ERROR, "Signup Failed", "Username already exists.");
            }
        });

        createAccountLayout.add(createAccountLabel, 0, 0);
        createAccountLayout.add(newUsernameLabel, 0, 1);
        createAccountLayout.add(newUsernameField, 1, 1);
        createAccountLayout.add(newPasswordLabel, 0, 2);
        createAccountLayout.add(newPasswordField, 1, 2);
        createAccountLayout.add(confirmPasswordLabel, 0, 3);
        createAccountLayout.add(confirmPasswordField, 1, 3);
        createAccountLayout.add(createAccountSubmitButton, 1, 4);
        createAccountLayout.add(backButton2, 0, 4);
        createAccountScene = new Scene(createAccountLayout, 300, 200);

        primaryStage.setScene(homeScene);
        primaryStage.show();
    }

    private boolean createUser(String username, String password) {
        if (userDatabase.containsKey(username)) {
            return false;
        }
        userDatabase.put(username, password);
        return true;
    }

    private boolean validateUser(String username, String password) {
        return userDatabase.containsKey(username) && userDatabase.get(username).equals(password);
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
