package org.example;

import javafx.scene.control.Alert;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DBCPDataSource {

    private static BasicDataSource ds = new BasicDataSource();

    static {
        ds.setUrl("jdbc:mysql://localhost:3306/zaalsportaccommodaties?useSSL=false");
        ds.setUsername("root");
        ds.setPassword("root");
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private DBCPDataSource(){

    }
    public static boolean ExecuteQuery(String strSQL, Map<Integer, String> valueMap){
        Connection con = null;

        try{
            con = getConnection();
            PreparedStatement stat = con.prepareStatement(strSQL);
            for(Integer key: valueMap.keySet()){
                stat.setString(key, valueMap.get(key));
            }

            int result = stat.executeUpdate();
            if(result == 1){
                System.out.println("Query is verwerkt");
            }
        }catch (SQLIntegrityConstraintViolationException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Deze gegevens zijn al aanwezig.");
            alert.showAndWait();
        }catch(SQLException se){
            System.out.println(se.getMessage());
        }finally{
            try{
                if(con != null){
                    con.close();
                }
            }catch(SQLException se){
                System.out.println(se.getMessage());
            }
        }
        return true;
    }
    public static Map<String,String> getSelectedZaal(String selectedZaal){
        Connection con = null;

        try {
            con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();
            ResultSet result = stat.executeQuery("Select * from `zalen` WHERE zaalnaam = '" + selectedZaal + "'");
            Map<String, String> zaalMap = new HashMap<>();

            if (result.next()) {
                zaalMap.put("zaalnaam", result.getString("zaalnaam"));
                zaalMap.put("accommodatie", result.getString("accommodatie"));
            }

            return zaalMap;
        } catch(SQLException se){
            System.out.println(se.getMessage());

        } finally{
            try{
                con.close();
            }catch(SQLException se){
                System.out.println(se.getMessage());
            }
        }
        return new HashMap<>();
    }
    public static Map<String,String> getSelectedZaalgegevens(String selectedZaalgegevens){
        Connection con = null;

        try {
            con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();
            ResultSet result = stat.executeQuery("Select * from `zaalgegevens` WHERE zaalnaam = '" + selectedZaalgegevens + "'");
            Map<String, String> zaalgegevensMap = new HashMap<>();

            if (result.next()) {
                zaalgegevensMap.put("zaalnaam", result.getString("zaalnaam"));
                zaalgegevensMap.put("website", result.getString("website"));
                zaalgegevensMap.put("telefoonnummer", result.getString("telefoonnummer"));

            }

            return zaalgegevensMap;
        } catch(SQLException se){
            System.out.println(se.getMessage());

        } finally{
            try{
                con.close();
            }catch(SQLException se){
                System.out.println(se.getMessage());
            }
        }
        return new HashMap<>();
    }
    public static Map<String,String> getSelectedZaallocatie(String selectedZaallocatie){
        Connection con = null;

        try {
            con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();
            ResultSet result = stat.executeQuery("Select * from `zaallocaties` WHERE zaalnaam = '" + selectedZaallocatie + "'");
            Map<String, String> zaallocatieMap = new HashMap<>();

            if (result.next()) {
                zaallocatieMap.put("zaalnaam", result.getString("zaalnaam"));
                zaallocatieMap.put("adres", result.getString("adres"));
                zaallocatieMap.put("postcode", result.getString("postcode"));
                zaallocatieMap.put("woonplaats", result.getString("woonplaats"));
                zaallocatieMap.put("xkoord", result.getString("xkoord"));
                zaallocatieMap.put("ykoord", result.getString("ykoord"));
            }

            return zaallocatieMap;
        } catch(SQLException se){
            System.out.println(se.getMessage());

        } finally{
            try{
                con.close();
            }catch(SQLException se){
                System.out.println(se.getMessage());
            }
        }
        return new HashMap<>();
    }
}
