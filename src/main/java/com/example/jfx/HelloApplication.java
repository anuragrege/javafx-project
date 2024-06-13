package com.example.jfx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import java.io.IOException;

public class HelloApplication extends Application {
    private Scene homeScene, loginScene, createAccountScene;
    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Welcome to the System");

        // Home Page
        Label welcomeLabel = new Label("Welcome to the System");
        welcomeLabel.setAlignment(Pos.CENTER);
        welcomeLabel.setFont(Font.font("Tahoma", FontWeight.SEMI_BOLD, 15));
        Button loginButton = new Button("Login");
        Button createAccountButton = new Button("Create Account");

        loginButton.setOnAction(e -> primaryStage.setScene(loginScene));
        createAccountButton.setOnAction(e -> primaryStage.setScene(createAccountScene));

        VBox homeLayout = new VBox(10);
        homeLayout.setPadding(new Insets(25));
        homeLayout.getChildren().addAll(welcomeLabel, loginButton, createAccountButton);
        homeScene = new Scene(homeLayout, 300, 200);

        // Login Scene
        GridPane loginLayout = new GridPane();
        loginLayout.setAlignment(Pos.CENTER);
        loginLayout.setVgap(20);
        loginLayout.setHgap(20);
        Label loginLabel = new Label("Login");
        loginLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        Label usernameLabel = new Label("Username:");
        Label passwordLabel = new Label("Password:");
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button loginSubmitButton = new Button("Login");
        Button backButton1 = new Button("Back");
        backButton1.setOnAction(e -> primaryStage.setScene(homeScene));
        loginLayout.add(loginLabel, 0, 0);
        loginLayout.add(usernameLabel, 0, 1);
        loginLayout.add(usernameField, 1, 1);
        loginLayout.add(passwordLabel, 0, 2);
        loginLayout.add(passwordField, 1, 2);
        loginLayout.add(loginSubmitButton, 1, 3);
        loginLayout.add(backButton1, 0, 3);
        loginScene = new Scene(loginLayout, 300, 200);


        //Create Account Scene
        GridPane createAccountLayout = new GridPane();
        createAccountLayout.setAlignment(Pos.CENTER);
        createAccountLayout.setVgap(10);
        createAccountLayout.setHgap(10);
        Label createAccountLabel = new Label("Create Account");
        createAccountLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        Label newUsernameLabel = new Label("Username:");
        Label newPasswordLabel = new Label("Password:");
        Label confirmPasswordLabel = new Label("Confirm Password:");
        TextField newUsernameField = new TextField();
        PasswordField newPasswordField = new PasswordField();
        PasswordField confirmPasswordField = new PasswordField();
        Button createAccountSubmitButton = new Button("Create Account");
        Button backButton2 = new Button("Back");

        backButton2.setOnAction(e -> primaryStage.setScene(homeScene));

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

    public static void main(String[] args) {
        launch();
    }
}