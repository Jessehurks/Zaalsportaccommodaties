package org.example.Controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.example.DBCPDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class ZaalController {
    private Connection con;
    public ZaalController() {

    }
    public void fillComboBox(ComboBox<String> cbAccommodatie) {
        //TODO: Dit moet nog in ZaalController
        try {
            con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();
            ResultSet result = stat.executeQuery("Select * from `accommodaties`");

            while (result.next()) {
                String strAccommodatie = result.getString("accommodatie");
                cbAccommodatie.getItems().addAll(strAccommodatie);
            }

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
    }
    public void fillFields(ListView<String> lvZaal, TextField txtZaalnaam, ComboBox<String> cbAccommodatie) {
        String selectedZaal = lvZaal.getSelectionModel().getSelectedItem().toString();
        if(selectedZaal != null){
            Map<String, String> zaalMap = DBCPDataSource.getSelectedZaal(selectedZaal);
            txtZaalnaam.setText(zaalMap.get("zaalnaam"));
            cbAccommodatie.getSelectionModel().select(zaalMap.get("accommodatie"));
        }
    }
    public void addZaal(String txtZaalnaam, String cbAccommodatie) {
        String strSQL = "insert into `zalen`(zaalnaam, accommodatie) " +
                "values (?, ?)";
        if(txtZaalnaam == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("U heeft een veld leeg gelaten.");
            alert.showAndWait();
            return;
        }

        Map<Integer, String> valueMap = new HashMap<Integer, String>();
        valueMap.put(1, txtZaalnaam);
        valueMap.put(2, cbAccommodatie);
        DBCPDataSource.ExecuteQuery(strSQL, valueMap);
    }

    public void updateZaal(String txtZaalnaam, String cbAccommodatie, String selectedZaal) {
        String strSQL = "update `zalen` SET zaalnaam = ?,  accommodatie = ?" +
                "WHERE zaalnaam = '" + selectedZaal + "'";
        if(txtZaalnaam == null || cbAccommodatie == null || selectedZaal == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("U heeft een veld leeg gelaten.");
            alert.showAndWait();
            return;
        }
        Map<Integer, String> valueMap = new HashMap<Integer, String>();
        valueMap.put(1, txtZaalnaam);
        valueMap.put(2, cbAccommodatie);
        DBCPDataSource.ExecuteQuery(strSQL, valueMap);
    }

    public void deleteZaal(String selectedZaal) {
        String strSQL = "DELETE FROM `zalen` WHERE zaalnaam = ? ";
        if(selectedZaal == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("U heeft een veld leeg gelaten.");
            alert.showAndWait();
            return;
        }

        Map<Integer, String> valueMap = new HashMap<Integer, String>();
        valueMap.put(1, selectedZaal);
        DBCPDataSource.ExecuteQuery(strSQL, valueMap);
    }
    public void refreshList(ListView<String> lvZaal){
        ObservableList<String> oList = FXCollections.observableArrayList();

        try {
            con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();
            ResultSet result = stat.executeQuery("Select * from `zalen`");

            while (result.next()) {
                String strZaalnaam = result.getString("zaalnaam");
                oList.add(strZaalnaam);
            }

        } catch(SQLException se){
            System.out.println(se.getMessage());

        } finally{
            try{
                lvZaal.setItems(null);
                lvZaal.setItems(oList);
                con.close();
            }catch(SQLException se){
                System.out.println(se.getMessage());
            }
        }
    }
}
