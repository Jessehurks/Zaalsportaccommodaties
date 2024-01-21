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
        loginScene.getStylesheets().add(("styles.css"));

        primaryStage.setScene(loginScene);
        primaryStage.show();

    }
    private Scene createLoginScene(){
        GridPane grid = new GridPane();
        grid.setAlignment(javafx.geometry.Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label usernameLabel = new Label("Gebruikersnaam");
        grid.add(usernameLabel, 0, 0);
        TextField usernameField = new TextField();
        grid.add(usernameField, 0, 1);

        Label passwordLabel = new Label("Wachtwoord:");
        grid.add(passwordLabel, 0, 2);
        PasswordField passwordField = new PasswordField();
        grid.add(passwordField, 0, 3);

        Button loginButton = new Button("Login");
        grid.add(loginButton, 0, 4);
        loginButton.setMinWidth(187);

        loginButton.setOnAction(e -> handleLogin(usernameField.getText(), passwordField.getText()));

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
            System.out.println("Login mislukt, vul een geldig gebruikersnaam of wachtwoord in.");
        }
    }

    private void showMainScreen() {
        Pane root = new Pane();
        MenuBar menuBar = new AccommodatieView(root);
        VBox vbox = new VBox(menuBar, root);
        Scene scene = new Scene(vbox, 1600, 720, Color.ORANGE);

        scene.getStylesheets().add(("styles.css"));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Zaalsportaccommodaties");
    }

    public static void main(String[] args) {
        launch();
    }

}