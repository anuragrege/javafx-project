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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HelloApplication extends Application {

    private Scene homeScene, loginScene, createAccountScene, deleteAccountScene;
    private Connection connection;

    @Override
    public void start(Stage primaryStage) {
        setupDatabase();

        primaryStage.setTitle("Welcome to the System");

        // Home Scene
        Label welcomeLabel = new Label("Welcome to the System");
        Button loginButton = new Button("Login");
        Button createAccountButton = new Button("Create Account");
        Button deleteAccountButton = new Button("Delete Account");

        loginButton.setOnAction(e -> primaryStage.setScene(loginScene));
        createAccountButton.setOnAction(e -> primaryStage.setScene(createAccountScene));
        deleteAccountButton.setOnAction(e -> primaryStage.setScene(deleteAccountScene));

        VBox homeLayout = new VBox(10);
        homeLayout.getChildren().addAll(welcomeLabel, loginButton, createAccountButton, deleteAccountButton);
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

        // Delete Account Scene
        GridPane deleteAccountLayout = new GridPane();
        deleteAccountLayout.setVgap(10);
        deleteAccountLayout.setHgap(10);
        Label deleteAccountLabel = new Label("Delete Account");
        Label deleteUsernameLabel = new Label("Username:");
        Label deletePasswordLabel = new Label("Password:");
        TextField deleteUsernameField = new TextField();
        PasswordField deletePasswordField = new PasswordField();
        Button deleteAccountSubmitButton = new Button("Delete Account");
        Button backButton3 = new Button("Back");

        backButton3.setOnAction(e -> primaryStage.setScene(homeScene));
        deleteAccountSubmitButton.setOnAction(e -> {
            if (deleteUser(deleteUsernameField.getText(), deletePasswordField.getText())) {
                showAlert(AlertType.INFORMATION, "Account Deleted", "Your account has been deleted.");
                primaryStage.setScene(homeScene);
            } else {
                showAlert(AlertType.ERROR, "Deletion Failed", "Invalid username or password.");
            }
        });

        deleteAccountLayout.add(deleteAccountLabel, 0, 0);
        deleteAccountLayout.add(deleteUsernameLabel, 0, 1);
        deleteAccountLayout.add(deleteUsernameField, 1, 1);
        deleteAccountLayout.add(deletePasswordLabel, 0, 2);
        deleteAccountLayout.add(deletePasswordField, 1, 2);
        deleteAccountLayout.add(deleteAccountSubmitButton, 1, 3);
        deleteAccountLayout.add(backButton3, 0, 3);
        deleteAccountScene = new Scene(deleteAccountLayout, 300, 200);

        primaryStage.setScene(homeScene);
        primaryStage.show();
    }

    private void setupDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:userDatabase.db");
            String createTableSQL = "CREATE TABLE IF NOT EXISTS users (username TEXT PRIMARY KEY, password TEXT)";
            try (PreparedStatement pstmt = connection.prepareStatement(createTableSQL)) {
                pstmt.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean createUser(String username, String password) {
        String insertUserSQL = "INSERT INTO users(username, password) VALUES(?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertUserSQL)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    private boolean validateUser(String username, String password) {
        String validateUserSQL = "SELECT password FROM users WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(validateUserSQL)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next() && rs.getString("password").equals(password)) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean deleteUser(String username, String password) {
        if (validateUser(username, password)) {
            String deleteUserSQL = "DELETE FROM users WHERE username = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(deleteUserSQL)) {
                pstmt.setString(1, username);
                pstmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
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

