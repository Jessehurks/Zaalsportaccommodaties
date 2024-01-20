package org.example.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import org.example.DBCPDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class ZaallocatieController {
    private Connection con;
    public ZaallocatieController(){}
    public void addZaallocatie(String cbZaalnaam, String txtAdres, String txtPostcode, String txtWoonplaats, String txtXkoord, String txtYkoord) {
        String strSQL = "insert into `zaallocaties`(zaalnaam, adres, postcode, woonplaats, xkoord, ykoord) " +
                "values (?, ?, ?, ?, ?, ?)";
        if(cbZaalnaam == null || txtAdres == null || txtPostcode == null || txtWoonplaats == null || txtXkoord == null || txtYkoord == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("U heeft een veld leeg gelaten.");
            alert.showAndWait();
            return;
        }

        Map<Integer, String> valueMap = new HashMap<Integer, String>();
        valueMap.put(1, cbZaalnaam);
        valueMap.put(2, txtAdres);
        valueMap.put(3, txtPostcode);
        valueMap.put(4, txtWoonplaats);
        valueMap.put(5, txtXkoord);
        valueMap.put(6, txtYkoord);
        DBCPDataSource.ExecuteQuery(strSQL, valueMap);
    }

    public void updateZaallocatie(String cbZaalnaam, String txtAdres, String txtPostcode, String txtWoonplaats, String txtXkoord, String txtYkoord, String selectedZaallocatie) {
        String strSQL = "update `zaallocaties` SET zaalnaam = ?,  adres = ?, postcode = ?, woonplaats = ?, xkoord = ?, ykoord = ?" +
                "WHERE zaalnaam = '" + selectedZaallocatie + "'";
        if(cbZaalnaam == null || txtAdres == null || txtPostcode == null || txtWoonplaats == null || txtXkoord == null || txtYkoord == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("U heeft een veld leeg gelaten.");
            alert.showAndWait();
            return;
        }
        Map<Integer, String> valueMap = new HashMap<Integer, String>();
        valueMap.put(1, cbZaalnaam);
        valueMap.put(2, txtAdres);
        valueMap.put(3, txtPostcode);
        valueMap.put(4, txtWoonplaats);
        valueMap.put(5, txtXkoord);
        valueMap.put(6, txtYkoord);
        DBCPDataSource.ExecuteQuery(strSQL, valueMap);
    }

    public void deleteZaallocatie(String selectedZaallocatie) {
        String strSQL = "DELETE FROM `zaallocaties` WHERE zaalnaam = ? ";
        if(selectedZaallocatie == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("U heeft een veld leeg gelaten.");
            alert.showAndWait();
            return;
        }

        Map<Integer, String> valueMap = new HashMap<Integer, String>();
        valueMap.put(1, selectedZaallocatie);
        DBCPDataSource.ExecuteQuery(strSQL, valueMap);
    }
    public void refreshList(ListView<String> lvZaallocatie){
        ObservableList<String> oList = FXCollections.observableArrayList();

        try {
            con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();
            ResultSet result = stat.executeQuery("Select * from `zaallocaties`");

            while (result.next()) {
                String strZaalnaam = result.getString("zaalnaam");
                oList.add(strZaalnaam);
            }

        } catch(SQLException se){
            System.out.println(se.getMessage());

        } finally{
            try{
                lvZaallocatie.setItems(null);
                lvZaallocatie.setItems(oList);
                con.close();
            }catch(SQLException se){
                System.out.println(se.getMessage());
            }
        }
    }

}
