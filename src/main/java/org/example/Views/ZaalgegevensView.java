package org.example.Views;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.example.Controllers.UserRepository;
import org.example.Controllers.ZaalgegevensController;
import org.example.DBCPDataSource;
import org.example.Models.Beheerder;
import org.example.Models.iGebruiker;
import javafx.scene.web.WebView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class ZaalgegevensView {
    private TextField txtTelefoonnummer, txtWebsite;
    private ComboBox cbZaalnaam;
    private Label lblZaalnaam, lblTelefoonnummer, lblWebsite;
    private Button btnOpslaan, btnUpdate, btnDelete;
    private ListView lvZaalgegevens;
    private ZaalgegevensController ZaalgegevensController;
    private Connection con;
    public ZaalgegevensView(Pane p){


        //Grid
        GridPane gridPane = new GridPane();
        iGebruiker gebruiker = UserRepository.getInstance().getGebruiker();
        ZaalgegevensController = new ZaalgegevensController();

        //Textfields
        txtTelefoonnummer = new TextField();
        txtTelefoonnummer.setPromptText("Telefoonnummer");

        txtWebsite = new TextField();
        txtWebsite.setPromptText("Website");

        //Labels
        lblZaalnaam = new Label("Zalen");
        lblTelefoonnummer = new Label("Telefoonnummer");
        lblWebsite = new Label("Website");

        //ComboBox
        cbZaalnaam = new ComboBox();

        //ListView
        lvZaalgegevens = new ListView<>();

        //Buttons
        btnOpslaan = new Button("Voeg toe");
        btnUpdate = new Button("Bewerken");
        btnDelete = new Button("Verwijderen");

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        ZaalgegevensController.refreshList(lvZaalgegevens);
        ZaalgegevensController.fillComboBox(cbZaalnaam);

        lvZaalgegevens.setOnMouseClicked(mouseEvent -> {
            ZaalgegevensController.fillFields(lvZaalgegevens, txtTelefoonnummer, txtWebsite, cbZaalnaam);
        });
        lvZaalgegevens.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnUpdate.setDisable(newValue == null);
            btnDelete.setDisable(newValue == null);
        });
        btnOpslaan.setOnMouseClicked(mouseEvent -> {
            ZaalgegevensController.addZaalgegevens(cbZaalnaam.getValue().toString(), txtTelefoonnummer.getText(), txtWebsite.getText());
            ZaalgegevensController.refreshList(lvZaalgegevens);
        });
        btnUpdate.setOnMouseClicked(mouseEvent -> {
            ZaalgegevensController.updateZaalgegevens(cbZaalnaam.getValue().toString(), txtTelefoonnummer.getText(), txtWebsite.getText(), lvZaalgegevens.getSelectionModel().getSelectedItem().toString());
            ZaalgegevensController.refreshList(lvZaalgegevens);
        });
        btnDelete.setOnMouseClicked(mouseEvent -> {
            ZaalgegevensController.deleteZaalgegevens( lvZaalgegevens.getSelectionModel().getSelectedItem().toString());
            ZaalgegevensController.refreshList(lvZaalgegevens);
        });
        //Styling
        gridPane.setHgap(15);
        gridPane.setVgap(5);
        gridPane.setPadding((new Insets(15)));

        gridPane.add(lblZaalnaam,0,0);
        gridPane.add(cbZaalnaam,0,1);
        gridPane.add(lblTelefoonnummer,0,2);
        gridPane.add(txtTelefoonnummer,0,3);
        gridPane.add(lblWebsite,0,4);
        gridPane.add(txtWebsite,0,5);
        gridPane.add(btnOpslaan,0,6);
        if(gebruiker instanceof Beheerder) {
            gridPane.add(btnUpdate,0,7);
            gridPane.add(btnDelete,0,8);
        }

        //Button width (gelijk aan TextFields)
        btnOpslaan.setMinWidth(187);
        btnUpdate.setMinWidth(187);
        btnDelete.setMinWidth(187);

        //TextField width
        txtTelefoonnummer.setMinWidth(187);
        txtWebsite.setMinWidth(187);
        cbZaalnaam.setMinWidth(187);
        lvZaalgegevens.relocate(300, 15);

        p.getChildren().addAll(gridPane, lvZaalgegevens);
    }
}
