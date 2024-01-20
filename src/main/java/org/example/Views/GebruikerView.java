package org.example.Views;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class GebruikerView {
    private TextField txtGebruikersnaam, txtWachtwoord;
    private ComboBox cbType;
    private Button btnOpslaan, btnUpdate, btnDelete;
    private ListView lvGebruikers;
    public GebruikerView(Pane p){
        //Grid
        GridPane gridPane = new GridPane();

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

        //Styling
        gridPane.setHgap(15);
        gridPane.setVgap(5);
        gridPane.setPadding((new Insets(15)));

        gridPane.add(txtGebruikersnaam,0,0);
        gridPane.add(txtWachtwoord,0,1);
        gridPane.add(cbType,0,2);
        gridPane.add(btnOpslaan,0,3);
        gridPane.add(btnUpdate,0,4);
        gridPane.add(btnDelete,0,5);

        lvGebruikers.relocate(300, 15);

        p.getChildren().addAll(gridPane, lvGebruikers);
    }
}
