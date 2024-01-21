package org.example;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.Controllers.LoginController;
import org.example.Models.Accommodatie;
import org.example.Models.iGebruiker;
import org.example.Views.AccommodatieView;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;


/**
 * JavaFX App
 */
public class App extends Application {
    private Stage primaryStage;
    private LoginController loginController;
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        Scene loginScene = createLoginScene();
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }
    private Scene createLoginScene(){
        // Create the layout for the login scene
        GridPane grid = new GridPane();
        grid.setAlignment(javafx.geometry.Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Add controls to the layout
        Label usernameLabel = new Label("Username:");
        grid.add(usernameLabel, 0, 0);
        TextField usernameField = new TextField();
        grid.add(usernameField, 1, 0);

        Label passwordLabel = new Label("Password:");
        grid.add(passwordLabel, 0, 1);
        PasswordField passwordField = new PasswordField();
        grid.add(passwordField, 1, 1);

        Button loginButton = new Button("Login");
        grid.add(loginButton, 1, 2);

        // Add event handler for the login button
        loginButton.setOnAction(e -> handleLogin(usernameField.getText(), passwordField.getText()));

        // Create the login scene
        return new Scene(grid, 400, 275);

    }
    private void handleLogin(String username, String password) {
        loginController = new LoginController();
        if (!username.isEmpty() && !password.isEmpty()) {
            boolean success = loginController.loginCheck(username, password);
            if(success){
                showMainScreen();
            }
        } else {
            System.out.println("Login failed. Please enter a valid username and password.");
        }
    }

    private void showMainScreen() {
        // Create the layout for the main screen
        GridPane grid = new GridPane();
        grid.setAlignment(javafx.geometry.Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Add controls to the layout for the main screen
        Label welcomeLabel = new Label("Welcome to the Main Screen!");
        grid.add(welcomeLabel, 0, 0);

        // Create the main screen scene
        Pane root = new Pane();
        MenuBar menuBar = new AccommodatieView(root);
        VBox vbox = new VBox(menuBar, root);
        Scene scene = new Scene(vbox, 850, 550, Color.ORANGE);

        // Set the main screen scene as the primary stage scene
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch();
    }

}