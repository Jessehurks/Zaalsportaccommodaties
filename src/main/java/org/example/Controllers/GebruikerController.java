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

public class GebruikerController {
    private Connection con;

    public GebruikerController(){

    }
    public void fillFields(TextField txtGebruikersnaam, TextField txtWachtwoord, ComboBox<String> cbType, ListView<String> lvGebruikers) {
        String selectedGebruikersnaam = lvGebruikers.getSelectionModel().getSelectedItem().toString();

        if(selectedGebruikersnaam != null){
            Map<String, String> gebruikerMap = DBCPDataSource.getSelectedGebruiker(selectedGebruikersnaam);
            txtGebruikersnaam.setText(gebruikerMap.get("gebruikersnaam"));
            txtWachtwoord.setText(gebruikerMap.get("wachtwoord"));
            cbType.getSelectionModel().select(gebruikerMap.get("type"));
        }
    }
    public void addGebruiker(String txtGebruikersnaam, String txtWachtwoord, String cbType) {
        String strSQL = "insert into `gebruikers`(gebruikersnaam, wachtwoord, type) " +
                "values (?, ?, ?)";
        if(txtGebruikersnaam == null || txtWachtwoord == null || cbType == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("U heeft een veld leeg gelaten.");
            alert.showAndWait();
            return;
        }

        Map<Integer, String> valueMap = new HashMap<Integer, String>();
        valueMap.put(1, txtGebruikersnaam);
        valueMap.put(2, txtWachtwoord);
        valueMap.put(3, cbType);
        DBCPDataSource.ExecuteQuery(strSQL, valueMap);
    }

    public void updateGebruiker(String txtGebruikersnaam, String txtWachtwoord, String cbType, String selectedGebruikersnaam) {
        String strSQL = "update `gebruikers` SET gebruikersnaam = ?,  wachtwoord = ?, type = ?" +
                "WHERE gebruikersnaam = '" + selectedGebruikersnaam + "'";
        if(txtGebruikersnaam == null || txtWachtwoord == null || cbType == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("U heeft een veld leeg gelaten.");
            alert.showAndWait();
            return;
        }
        Map<Integer, String> valueMap = new HashMap<Integer, String>();
        valueMap.put(1, txtGebruikersnaam);
        valueMap.put(2, txtWachtwoord);
        valueMap.put(3, cbType);
        DBCPDataSource.ExecuteQuery(strSQL, valueMap);
    }

    public void deleteGebruiker(String selectedGebruikersnaam) {
        String strSQL = "DELETE FROM `gebruikers` WHERE gebruikersnaam = ? ";
        if(selectedGebruikersnaam == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("U heeft een veld leeg gelaten.");
            alert.showAndWait();
            return;
        }

        Map<Integer, String> valueMap = new HashMap<Integer, String>();
        valueMap.put(1, selectedGebruikersnaam);
        DBCPDataSource.ExecuteQuery(strSQL, valueMap);
    }
    public void refreshList(ListView<String> lvGebruikers){
        ObservableList<String> oList = FXCollections.observableArrayList();

        try {
            con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();
            ResultSet result = stat.executeQuery("Select * from `gebruikers`");

            while (result.next()) {
                String strGebruikersnaam = result.getString("gebruikersnaam");
                oList.add(strGebruikersnaam);
            }

        } catch(SQLException se){
            System.out.println(se.getMessage());

        } finally{
            try{
                lvGebruikers.setItems(null);
                lvGebruikers.setItems(oList);
                con.close();
            }catch(SQLException se){
                System.out.println(se.getMessage());
            }
        }
    }
}
