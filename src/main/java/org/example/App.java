package org.example;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.Models.Accommodatie;
import org.example.Views.AccommodatieView;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        Pane root = new Pane();
        MenuBar menuBar = new AccommodatieView(root);
        VBox vbox = new VBox(menuBar, root);
        Scene scene = new Scene(vbox, 850, 550, Color.ORANGE);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}