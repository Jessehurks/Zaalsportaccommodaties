package org.example.Controllers;

import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.example.DBCPDataSource;
import org.example.Models.Accommodatie;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;


public class AccommodatieController {
    private Connection con;
    public AccommodatieController(){

    }
    public void fillFields(ListView<String> lvAccommodatie, TextField txtAccommodatie) {
        String selectedAccommodatie = lvAccommodatie.getSelectionModel().getSelectedItem();
        if(selectedAccommodatie != null){
            txtAccommodatie.setText(selectedAccommodatie);
        }
    }
    public void addAccommodatie(String txtAccommodatie) {
        String strSQL = "insert into `accommodaties`(accommodatie) " +
                "values (?)";
        if(txtAccommodatie == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("U heeft een veld leeg gelaten.");
            alert.showAndWait();
            return;
        }

        Map<Integer, String> valueMap = new HashMap<Integer, String>();
        valueMap.put(1, txtAccommodatie);
        DBCPDataSource.ExecuteQuery(strSQL, valueMap);
    }

    public void updateAccommodatie(String txtAccommodatie, String selectedAccommodatie) {
        String strSQL = "update `accommodaties` SET accommodatie = ? " +
                "WHERE accommodatie = '" + selectedAccommodatie + "'";
        if(txtAccommodatie == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("U heeft een veld leeg gelaten.");
            alert.showAndWait();
            return;
        }

        Map<Integer, String> valueMap = new HashMap<Integer, String>();
        valueMap.put(1, txtAccommodatie);
        DBCPDataSource.ExecuteQuery(strSQL, valueMap);
    }

    public void deleteAccommodatie(String selectedAccommodatie) {
        String strSQL = "DELETE FROM `accommodaties` WHERE accommodatie = ? ";
        if(selectedAccommodatie == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("U heeft een veld leeg gelaten.");
            alert.showAndWait();
            return;
        }

        Map<Integer, String> valueMap = new HashMap<Integer, String>();
        valueMap.put(1, selectedAccommodatie);
        DBCPDataSource.ExecuteQuery(strSQL, valueMap);
    }
    public void refreshList(ListView<String> lvAccommodatie){
        ObservableList<String> oList = FXCollections.observableArrayList();

        try {
            con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();
            ResultSet result = stat.executeQuery("Select * from `accommodaties`");

            while (result.next()) {
                String strAccommodatie = result.getString("accommodatie");
                oList.add(strAccommodatie);
            }
            Accommodatie[] accommodaties = loadAccommodatieJSON();
            for (Accommodatie a : accommodaties){
                oList.add(a.getAccommodatie());
            }
        } catch(SQLException se){
            System.out.println(se.getMessage());

        } finally{
            try{
                lvAccommodatie.setItems(null);
                lvAccommodatie.setItems(oList);
                con.close();
            }catch(SQLException se){
                System.out.println(se.getMessage());
            }
        }
    }
    public Accommodatie[] loadAccommodatieJSON() {
        Gson gson = new Gson();

        try (Reader reader = new FileReader("C:\\Users\\Jesse\\IdeaProjects\\Zaalsportaccommodaties\\src\\main\\java\\org\\example\\jsonfiles\\jesse_json.json")) {

            return gson.fromJson(reader, Accommodatie[].class);


        } catch(IOException e){
            e.printStackTrace();
        }
        return new Accommodatie[0];
    }

}
