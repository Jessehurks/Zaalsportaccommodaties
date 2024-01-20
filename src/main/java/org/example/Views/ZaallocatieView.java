package org.example.Views;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.example.Controllers.ZaallocatieController;
import org.example.DBCPDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class ZaallocatieView {
    private TextField txtAdres, txtPostcode, txtWoonplaats, txtXkoord, txtYkoord;
    private ComboBox cbZaalnaam;
    private Button btnOpslaan, btnUpdate, btnDelete;
    private ListView lvZaallocatie;
    private ZaallocatieController ZaallocatieController;
    private Connection con;
    public ZaallocatieView(Pane p){
       //Grid
        GridPane gridPane = new GridPane();
        ZaallocatieController = new ZaallocatieController();
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

        //ComboBox
        cbZaalnaam = new ComboBox();

        //ListView
        lvZaallocatie = new ListView<>();

        //Buttons
        btnOpslaan = new Button("Voeg toe");
        btnUpdate = new Button("Bewerken");
        btnDelete = new Button("Verwijderen");

        ZaallocatieController.refreshList(lvZaallocatie);

        //TODO: Dit moet nog in ZaalgegevensController
        try {
            con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();
            ResultSet result = stat.executeQuery("Select * from `zalen`");

            while (result.next()) {
                String strZaalnaam = result.getString("zaalnaam");
                cbZaalnaam.getItems().addAll(strZaalnaam);
            }
            lvZaallocatie.setOnMouseClicked(mouseEvent -> {
                String selectedZaallocatie = lvZaallocatie.getSelectionModel().getSelectedItem().toString();
                if(selectedZaallocatie != null){
                    Map<String, String> zaallocatieMap = DBCPDataSource.getSelectedZaallocatie(selectedZaallocatie);
                    txtAdres.setText(zaallocatieMap.get("adres"));
                    txtPostcode.setText(zaallocatieMap.get("postcode"));
                    txtWoonplaats.setText(zaallocatieMap.get("woonplaats"));
                    txtXkoord.setText(zaallocatieMap.get("xkoord"));
                    txtYkoord.setText(zaallocatieMap.get("ykoord"));
                    cbZaalnaam.getSelectionModel().select(zaallocatieMap.get("zaalnaam"));
                }
            });
        } catch(SQLException se){
            System.out.println(se.getMessage());

        } finally{
            try{
                cbZaalnaam.getSelectionModel().selectFirst();
                con.close();
            }catch(SQLException se){
                System.out.println(se.getMessage());
            }
        }
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

        gridPane.add(cbZaalnaam,0,0);
        gridPane.add(txtAdres,0,1);
        gridPane.add(txtPostcode,0,2);
        gridPane.add(txtWoonplaats,0,3);
        gridPane.add(txtXkoord,0,4);
        gridPane.add(txtYkoord,0,5);
        gridPane.add(btnOpslaan,0,6);
        gridPane.add(btnUpdate,0,7);
        gridPane.add(btnDelete,0,8);

        lvZaallocatie.relocate(300, 15);

        p.getChildren().addAll(gridPane, lvZaallocatie);
    }
}
