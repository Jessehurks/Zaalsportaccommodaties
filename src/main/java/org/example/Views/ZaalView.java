package org.example.Views;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.example.Controllers.AccommodatieController;
import org.example.Controllers.UserRepository;
import org.example.Controllers.ZaalController;
import org.example.DBCPDataSource;
import org.example.Models.Accommodatie;
import org.example.Models.Beheerder;
import org.example.Models.iGebruiker;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class ZaalView {
    private TextField txtZaalnaam;
    private ComboBox cbAccommodatie;
    private Label lblZaalnaam, lblAccommodatie;
    private Button btnOpslaan, btnUpdate, btnDelete;
    private ListView lvZaal;
    private ZaalController ZaalController;
    private Accommodatie accommodatie;
    private Connection con;
    public ZaalView(Pane p){

        //Grid
        GridPane gridPane = new GridPane();
        iGebruiker gebruiker = UserRepository.getInstance().getGebruiker();

        ZaalController = new ZaalController();
        accommodatie = new Accommodatie("");
        //Textfields
        txtZaalnaam = new TextField();
        txtZaalnaam.setPromptText("Zaalnaam");

        //Labels
        lblZaalnaam = new Label("Zaalnaam");
        lblAccommodatie = new Label("Accommodaties");

        //ComboBox
        cbAccommodatie = new ComboBox<>();

        //ListView
        lvZaal = new ListView<>();

        //Buttons
        btnOpslaan = new Button("Voeg toe");
        btnUpdate = new Button("Bewerken");
        btnDelete = new Button("Verwijderen");

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        ZaalController.refreshList(lvZaal);
        ZaalController.fillComboBox(cbAccommodatie);

        lvZaal.setOnMouseClicked(mouseEvent -> {
            ZaalController.fillFields(lvZaal, txtZaalnaam, cbAccommodatie);
        });
        lvZaal.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnUpdate.setDisable(newValue == null);
            btnDelete.setDisable(newValue == null);
        });
        btnOpslaan.setOnMouseClicked(mouseEvent -> {
            ZaalController.addZaal(txtZaalnaam.getText(), cbAccommodatie.getValue().toString());
            ZaalController.refreshList(lvZaal);
        });
        btnUpdate.setOnMouseClicked(mouseEvent -> {
            ZaalController.updateZaal(txtZaalnaam.getText(), cbAccommodatie.getValue().toString(), lvZaal.getSelectionModel().getSelectedItem().toString());
            ZaalController.refreshList(lvZaal);
        });
        btnDelete.setOnMouseClicked(mouseEvent -> {
            ZaalController.deleteZaal( lvZaal.getSelectionModel().getSelectedItem().toString());
            ZaalController.refreshList(lvZaal);
        });

        //Styling
        gridPane.setHgap(15);
        gridPane.setVgap(5);
        gridPane.setPadding((new Insets(15)));

        gridPane.add(lblAccommodatie, 0, 0);
        gridPane.add(cbAccommodatie,0,1);
        gridPane.add(lblZaalnaam, 0, 2);
        gridPane.add(txtZaalnaam,0,3);
        gridPane.add(btnOpslaan,0,4);
        if(gebruiker instanceof Beheerder) {
            gridPane.add(btnUpdate,0,5);
            gridPane.add(btnDelete,0,6);
        }
        //Button width (gelijk aan TextFields)
        btnOpslaan.setMinWidth(187);
        btnUpdate.setMinWidth(187);
        btnDelete.setMinWidth(187);

        //TextField width
        cbAccommodatie.setMinWidth(187);
        txtZaalnaam.setMinWidth(187);

        lvZaal.relocate(300, 15);

        p.getChildren().addAll(gridPane, lvZaal);
    }
}
