package org.example.Views;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import org.example.Controllers.UserRepository;
import org.example.Controllers.ZaallocatieController;
import org.example.DBCPDataSource;
import org.example.Models.Beheerder;
import org.example.Models.iGebruiker;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class ZaallocatieView {
    private TextField txtAdres, txtPostcode, txtWoonplaats, txtXkoord, txtYkoord;
    private Label lblZaalnaam, lblAdres, lblPostcode, lblWoonplaats, lblXkoord, lblYkoord;
    private ComboBox cbZaalnaam;
    private Button btnOpslaan, btnUpdate, btnDelete;
    private ListView lvZaallocatie;
    private ZaallocatieController ZaallocatieController;
    private Connection con;
    private WebView webView;

    public ZaallocatieView(Pane p){
        //WebView
        webView = new WebView();

       //Grid
        GridPane gridPane = new GridPane();
        ZaallocatieController = new ZaallocatieController();
        iGebruiker gebruiker = UserRepository.getInstance().getGebruiker();

        //Textfields
        txtAdres = new TextField();
        txtAdres.setPromptText("Adres");

        txtPostcode = new TextField();
        txtPostcode.setPromptText("Postcode");

        txtWoonplaats = new TextField();
        txtWoonplaats.setPromptText("Woonplaats");

        txtXkoord = new TextField();
        txtXkoord.setPromptText("X-Koördinaten");

        txtYkoord = new TextField();
        txtYkoord.setPromptText("Y-Koördinaten");

        //Labels
        lblZaalnaam = new Label("Zalen");
        lblAdres = new Label("Adres");
        lblPostcode = new Label("Postcode");
        lblWoonplaats = new Label("Woonplaats");
        lblXkoord = new Label("X-Koördinaten");
        lblYkoord = new Label("Y-Koördinaten");

        //ComboBox
        cbZaalnaam = new ComboBox();

        //ListView
        lvZaallocatie = new ListView<>();

        //Buttons
        btnOpslaan = new Button("Voeg toe");
        btnUpdate = new Button("Bewerken");
        btnDelete = new Button("Verwijderen");

        ZaallocatieController.refreshList(lvZaallocatie);
        ZaallocatieController.fillComboBox(cbZaalnaam);

        lvZaallocatie.setOnMouseClicked(mouseEvent -> {
            ZaallocatieController.fillFields(lvZaallocatie, txtAdres, txtPostcode, txtWoonplaats, txtXkoord, txtYkoord, cbZaalnaam);
            webView.getEngine().load("https://www.openstreetmap.org/export/embed.html?bbox=" + txtYkoord.getText() + "%2C" + txtXkoord.getText());
        });
        btnOpslaan.setOnMouseClicked(mouseEvent -> {
            ZaallocatieController.addZaallocatie(cbZaalnaam.getValue().toString(), txtAdres.getText(), txtPostcode.getText(),txtWoonplaats.getText(), txtXkoord.getText(), txtYkoord.getText());
            ZaallocatieController.refreshList(lvZaallocatie);
        });
        btnUpdate.setOnMouseClicked(mouseEvent -> {
            ZaallocatieController.updateZaallocatie(cbZaalnaam.getValue().toString(), txtAdres.getText(), txtPostcode.getText(),txtWoonplaats.getText(), txtXkoord.getText(), txtYkoord.getText(), lvZaallocatie.getSelectionModel().getSelectedItem().toString());
            ZaallocatieController.refreshList(lvZaallocatie);
        });
        btnDelete.setOnMouseClicked(mouseEvent -> {
            ZaallocatieController.deleteZaallocatie( lvZaallocatie.getSelectionModel().getSelectedItem().toString());
            ZaallocatieController.refreshList(lvZaallocatie);
        });

        //Styling
        gridPane.setHgap(15);
        gridPane.setVgap(5);
        gridPane.setPadding((new Insets(15)));

        gridPane.add(lblZaalnaam,0,0);
        gridPane.add(cbZaalnaam,0,1);
        gridPane.add(lblAdres,0,2);
        gridPane.add(txtAdres,0,3);
        gridPane.add(lblPostcode,0,4);
        gridPane.add(txtPostcode,0,5);
        gridPane.add(lblWoonplaats,0,6);
        gridPane.add(txtWoonplaats,0,7);
        gridPane.add(lblXkoord,0,8);
        gridPane.add(txtXkoord,0,9);
        gridPane.add(lblYkoord,0,10);
        gridPane.add(txtYkoord,0,11);
        gridPane.add(btnOpslaan,0,12);

        if(gebruiker instanceof Beheerder) {
            gridPane.add(btnUpdate,0,13);
            gridPane.add(btnDelete,0,14);
        }

        //Button width (gelijk aan TextFields)
        btnOpslaan.setMinWidth(187);
        btnUpdate.setMinWidth(187);
        btnDelete.setMinWidth(187);

        //TextField width
        txtAdres.setMinWidth(187);
        txtPostcode.setMinWidth(187);
        txtWoonplaats.setMinWidth(187);
        txtXkoord.setMinWidth(187);
        txtYkoord.setMinWidth(187);
        cbZaalnaam.setMinWidth(187);

        lvZaallocatie.relocate(300, 15);
        webView.relocate(600, 15);

        p.getChildren().addAll(gridPane, lvZaallocatie, webView);
    }
}
