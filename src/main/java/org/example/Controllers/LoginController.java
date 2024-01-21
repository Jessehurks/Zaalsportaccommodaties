package org.example.Controllers;

import javafx.scene.layout.Pane;
import org.example.DBCPDataSource;
import org.example.Models.Beheerder;
import org.example.Models.Zaaleigenaar;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginController {
    private Connection con;
    public LoginController(){

    }
    public boolean loginCheck(String username, String password) {
        try {
            con = DBCPDataSource.getConnection();
            Statement stat = con.createStatement();
            ResultSet result = stat.executeQuery
                    ("Select * from `gebruikers` where gebruikersnaam = '" + username + "' and wachtwoord = '" + password + "'");
            while (result.next()) {
                String strGebruikersnaam = result.getString("gebruikersnaam");
                String strWachtwoord = result.getString("wachtwoord");
                String type = result.getString("type");
                UserRepository ur = UserRepository.getInstance();

                if(type.equals("Beheerder")){
                    ur.setGebruiker(new Beheerder(strGebruikersnaam, strWachtwoord, type));
                }else if(type.equals("Zaaleigenaar")){
                    ur.setGebruiker(new Zaaleigenaar(strGebruikersnaam, strWachtwoord, type));
                }
                return true;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
}
