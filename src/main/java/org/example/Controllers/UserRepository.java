package org.example.Controllers;

import org.example.Models.iGebruiker;

public class UserRepository {
    private static UserRepository instance;
    private static iGebruiker gebruiker;
    private UserRepository(){
    }
    public static UserRepository getInstance() {
        if(instance == null){
            instance = new UserRepository();
        }
        return instance;
    }
    public iGebruiker getGebruiker() {
        return gebruiker;
    }

    public void setGebruiker(iGebruiker gebruiker) {
        UserRepository.gebruiker = gebruiker;
    }
}
