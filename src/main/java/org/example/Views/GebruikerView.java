package org.example.Views;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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

        GebruikerController.refreshList(lvGebruikers);

        //TODO: Dit moet nog in ZaalController
        try {
            con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();
            ResultSet result = stat.executeQuery("Select * from `gebruikers`");

            while (result.next()) {
                String strType = result.getString("type");
                cbType.getItems().addAll(strType);
            }
            lvGebruikers.setOnMouseClicked(mouseEvent -> {
                String selectedGebruikersnaam = lvGebruikers.getSelectionModel().getSelectedItem().toString();
                if(selectedGebruikersnaam != null){
                    Map<String, String> gebruikerMap = DBCPDataSource.getSelectedGebruiker(selectedGebruikersnaam);
                    txtGebruikersnaam.setText(gebruikerMap.get("gebruikersnaam"));
                    txtWachtwoord.setText(gebruikerMap.get("wachtwoord"));
                    cbType.getSelectionModel().select(gebruikerMap.get("type"));
                }
            });
        } catch(SQLException se){
            System.out.println(se.getMessage());

        } finally{
            try{
                cbType.getSelectionModel().selectFirst();
                con.close();
            }catch(SQLException se){
                System.out.println(se.getMessage());
            }
        }


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
