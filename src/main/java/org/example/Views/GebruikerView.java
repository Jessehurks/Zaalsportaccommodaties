package org.example.Views;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.example.Controllers.GebruikerController;
import org.example.DBCPDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class GebruikerView {
    private TextField txtGebruikersnaam, txtWachtwoord;
    private Label lblGebruikersnaam, lblWachtwoord, lblType;
    private ComboBox cbType;
    private Button btnOpslaan, btnUpdate, btnDelete;
    private ListView lvGebruikers;
    private GebruikerController GebruikerController;
    private Connection con;
    public GebruikerView(Pane p){
        //Grid
        GridPane gridPane = new GridPane();
        GebruikerController = new GebruikerController();

        //Textfields
        txtGebruikersnaam = new TextField();
        txtGebruikersnaam.setPromptText("Gebruikersnaam");

        txtWachtwoord = new TextField();
        txtWachtwoord.setPromptText("Wachtwoord");

        //ComboBox
        cbType = new ComboBox<String>();
        cbType.getItems().addAll("Beheerder", "Zaaleigenaar");

        //ListView
        lvGebruikers = new ListView<>();

        //Buttons
        btnOpslaan = new Button("Voeg toe");
        btnUpdate = new Button("Bewerken");
        btnDelete = new Button("Verwijderen");

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        //Labels
        lblGebruikersnaam = new Label("Gebruikersnaam");
        lblWachtwoord = new Label("Wachtwoord");
        lblType = new Label("Type");

        GebruikerController.refreshList(lvGebruikers);

        lvGebruikers.setOnMouseClicked(mouseEvent -> {
            GebruikerController.fillFields(txtGebruikersnaam, txtWachtwoord, cbType, lvGebruikers);
        });
        lvGebruikers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnUpdate.setDisable(newValue == null);
            btnDelete.setDisable(newValue == null);
        });
        btnOpslaan.setOnMouseClicked(mouseEvent -> {
            GebruikerController.addGebruiker(txtGebruikersnaam.getText(),txtWachtwoord.getText(), cbType.getValue().toString());
            GebruikerController.refreshList(lvGebruikers);
        });
        btnUpdate.setOnMouseClicked(mouseEvent -> {
            GebruikerController.updateGebruiker(txtGebruikersnaam.getText(),txtWachtwoord.getText(), cbType.getValue().toString(), lvGebruikers.getSelectionModel().getSelectedItem().toString());
            GebruikerController.refreshList(lvGebruikers);
        });
        btnDelete.setOnMouseClicked(mouseEvent -> {
            GebruikerController.deleteGebruiker( lvGebruikers.getSelectionModel().getSelectedItem().toString());
            GebruikerController.refreshList(lvGebruikers);
        });

        //Styling
        gridPane.setHgap(15);
        gridPane.setVgap(5);
        gridPane.setPadding((new Insets(15)));

        gridPane.add(lblGebruikersnaam,0,0);
        gridPane.add(txtGebruikersnaam,0,1);
        gridPane.add(lblWachtwoord,0,2);
        gridPane.add(txtWachtwoord,0,3);
        gridPane.add(lblType,0,4);
        gridPane.add(cbType,0,5);
        gridPane.add(btnOpslaan,0,6);
        gridPane.add(btnUpdate,0,7);
        gridPane.add(btnDelete,0,8);

        //Button width (gelijk aan TextFields)
        btnOpslaan.setMinWidth(187);
        btnUpdate.setMinWidth(187);
        btnDelete.setMinWidth(187);

        //TextField width
        txtGebruikersnaam.setMinWidth(187);
        txtWachtwoord.setMinWidth(187);
        cbType.setMinWidth(187);
        lvGebruikers.relocate(300, 15);

        p.getChildren().addAll(gridPane, lvGebruikers);
    }
}
