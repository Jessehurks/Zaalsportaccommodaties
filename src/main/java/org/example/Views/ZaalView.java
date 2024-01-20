package org.example.Views;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.example.Controllers.AccommodatieController;
import org.example.Controllers.ZaalController;
import org.example.DBCPDataSource;
import org.example.Models.Accommodatie;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class ZaalView {
    private TextField txtZaalnaam;
    private ComboBox cbAccommodatie;
    private Button btnOpslaan, btnUpdate, btnDelete;
    private ListView lvZaal;
    private ZaalController ZaalController;
    private Accommodatie accommodatie;
    private Connection con;
    public ZaalView(Pane p){

        //Grid
        GridPane gridPane = new GridPane();
        ZaalController = new ZaalController();
        accommodatie = new Accommodatie("");
        //Textfields
        txtZaalnaam = new TextField();
        txtZaalnaam.setPromptText("Zaalnaam");

        //ComboBox
        cbAccommodatie = new ComboBox<>();

        //ListView
        lvZaal = new ListView<>();

        //Buttons
        btnOpslaan = new Button("Voeg toe");
        btnUpdate = new Button("Bewerken");
        btnDelete = new Button("Verwijderen");

        ZaalController.refreshList(lvZaal);
        //TODO: Dit moet nog in ZaalController
        try {
            con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();
            ResultSet result = stat.executeQuery("Select * from `accommodaties`");

            while (result.next()) {
                String strAccommodatie = result.getString("accommodatie");
                cbAccommodatie.getItems().addAll(strAccommodatie);
            }
            lvZaal.setOnMouseClicked(mouseEvent -> {
                String selectedZaal = lvZaal.getSelectionModel().getSelectedItem().toString();
                if(selectedZaal != null){
                    Map<String, String> zaalMap = DBCPDataSource.getSelectedZaal(selectedZaal);
                    txtZaalnaam.setText(zaalMap.get("zaalnaam"));
                    cbAccommodatie.getSelectionModel().select(zaalMap.get("accommodatie"));
                }
            });
        } catch(SQLException se){
            System.out.println(se.getMessage());

        } finally{
            try{
                cbAccommodatie.getSelectionModel().selectFirst();
                con.close();
            }catch(SQLException se){
                System.out.println(se.getMessage());
            }
        }


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

        gridPane.add(txtZaalnaam,0,0);
        gridPane.add(cbAccommodatie,0,1);
        gridPane.add(btnOpslaan,0,2);
        gridPane.add(btnUpdate,0,3);
        gridPane.add(btnDelete,0,4);

        lvZaal.relocate(300, 15);

        p.getChildren().addAll(gridPane, lvZaal);
    }
}
