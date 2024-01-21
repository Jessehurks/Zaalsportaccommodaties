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

public class ZaalgegevensController {
    private Connection con;
    public ZaalgegevensController(){

    }

    public void fillComboBox(ComboBox<String> cbZaalnaam) {
        //TODO: Dit moet nog in ZaalgegevensController
        try {
            con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();
            ResultSet result = stat.executeQuery("Select * from `zalen`");

            while (result.next()) {
                String strZaalnaam = result.getString("zaalnaam");
                cbZaalnaam.getItems().addAll(strZaalnaam);
            }

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
    }

    public void fillFields(ListView<String> lvZaalgegevens, TextField txtTelefoonnummer, TextField txtWebsite, ComboBox<String> cbZaalnaam) {
        String selectedZaalgegevens = lvZaalgegevens.getSelectionModel().getSelectedItem();
        if(selectedZaalgegevens != null){
            Map<String, String> zaalgegevensMap = DBCPDataSource.getSelectedZaalgegevens(selectedZaalgegevens);
            txtTelefoonnummer.setText(zaalgegevensMap.get("telefoonnummer"));
            txtWebsite.setText(zaalgegevensMap.get("website"));
            cbZaalnaam.getSelectionModel().select(zaalgegevensMap.get("zaalnaam"));
        }
    }
    public void addZaalgegevens(String cbZaalnaam, String txtTelefoonnummer, String txtWebsite) {
        String strSQL = "insert into `zaalgegevens`(zaalnaam, telefoonnummer, website) " +
                "values (?, ?, ?)";
        if(cbZaalnaam == null || txtTelefoonnummer == null || txtWebsite == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("U heeft een veld leeg gelaten.");
            alert.showAndWait();
            return;
        }

        Map<Integer, String> valueMap = new HashMap<Integer, String>();
        valueMap.put(1, cbZaalnaam);
        valueMap.put(2, txtTelefoonnummer);
        valueMap.put(3, txtWebsite);
        DBCPDataSource.ExecuteQuery(strSQL, valueMap);
    }

    public void updateZaalgegevens(String cbZaalnaam, String txtTelefoonnummer, String txtWebsite, String selectedZaalgegevens) {
        String strSQL = "update `zaalgegevens` SET zaalnaam = ?,  telefoonnummer = ?, website = ?" +
                "WHERE zaalnaam = '" + selectedZaalgegevens + "'";
        if(cbZaalnaam == null || txtTelefoonnummer == null || txtWebsite == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("U heeft een veld leeg gelaten.");
            alert.showAndWait();
            return;
        }
        Map<Integer, String> valueMap = new HashMap<Integer, String>();
        valueMap.put(1, cbZaalnaam);
        valueMap.put(2, txtTelefoonnummer);
        valueMap.put(3, txtWebsite);
        DBCPDataSource.ExecuteQuery(strSQL, valueMap);
    }

    public void deleteZaalgegevens(String selectedZaalgegevens) {
        String strSQL = "DELETE FROM `zaalgegevens` WHERE zaalnaam = ? ";
        if(selectedZaalgegevens == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("U heeft een veld leeg gelaten.");
            alert.showAndWait();
            return;
        }

        Map<Integer, String> valueMap = new HashMap<Integer, String>();
        valueMap.put(1, selectedZaalgegevens);
        DBCPDataSource.ExecuteQuery(strSQL, valueMap);
    }
    public void refreshList(ListView<String> lvZaalgegevens){
        ObservableList<String> oList = FXCollections.observableArrayList();

        try {
            con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();
            ResultSet result = stat.executeQuery("Select * from `zaalgegevens`");

            while (result.next()) {
                String strZaalnaam = result.getString("zaalnaam");
                oList.add(strZaalnaam);
            }

        } catch(SQLException se){
            System.out.println(se.getMessage());

        } finally{
            try{
                lvZaalgegevens.setItems(null);
                lvZaalgegevens.setItems(oList);
                con.close();
            }catch(SQLException se){
                System.out.println(se.getMessage());
            }
        }
    }
}
