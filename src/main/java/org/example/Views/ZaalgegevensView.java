package org.example.Views;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.example.Controllers.ZaalgegevensController;
import org.example.DBCPDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class ZaalgegevensView {
    private TextField txtTelefoonnummer, txtWebsite;
    private ComboBox cbZaalnaam;
    private Button btnOpslaan, btnUpdate, btnDelete;
    private ListView lvZaalgegevens;
    private ZaalgegevensController ZaalgegevensController;
    private Connection con;
    public ZaalgegevensView(Pane p){
        //Grid
        GridPane gridPane = new GridPane();
        ZaalgegevensController = new ZaalgegevensController();

        //Textfields
        txtTelefoonnummer = new TextField();
        txtTelefoonnummer.setPromptText("Telefoonnummer");

        txtWebsite = new TextField();
        txtWebsite.setPromptText("Website");

        //ComboBox
        cbZaalnaam = new ComboBox();

        //ListView
        lvZaalgegevens = new ListView<>();

        //Buttons
        btnOpslaan = new Button("Voeg toe");
        btnUpdate = new Button("Bewerken");
        btnDelete = new Button("Verwijderen");

        ZaalgegevensController.refreshList(lvZaalgegevens);

        //TODO: Dit moet nog in ZaalgegevensController
        try {
            con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();
            ResultSet result = stat.executeQuery("Select * from `zalen`");

            while (result.next()) {
                String strZaalnaam = result.getString("zaalnaam");
                cbZaalnaam.getItems().addAll(strZaalnaam);
            }
            lvZaalgegevens.setOnMouseClicked(mouseEvent -> {
                String selectedZaalgegevens = lvZaalgegevens.getSelectionModel().getSelectedItem().toString();
                if(selectedZaalgegevens != null){
                    Map<String, String> zaalgegevensMap = DBCPDataSource.getSelectedZaalgegevens(selectedZaalgegevens);
                    txtTelefoonnummer.setText(zaalgegevensMap.get("telefoonnummer"));
                    txtWebsite.setText(zaalgegevensMap.get("website"));
                    cbZaalnaam.getSelectionModel().select(zaalgegevensMap.get("zaalnaam"));
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

        gridPane.add(cbZaalnaam,0,0);
        gridPane.add(txtTelefoonnummer,0,1);
        gridPane.add(txtWebsite,0,2);
        gridPane.add(btnOpslaan,0,3);
        gridPane.add(btnUpdate,0,4);
        gridPane.add(btnDelete,0,5);

        lvZaalgegevens.relocate(300, 15);

        p.getChildren().addAll(gridPane, lvZaalgegevens);
    }
}
