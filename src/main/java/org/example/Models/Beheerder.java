package org.example.Models;

public class Beheerder extends Gebruiker {
    public Beheerder(String gebruikersnaam, String wachtwoord, String type){
        this.gebruikersnaam = gebruikersnaam;
        this.wachtwoord = wachtwoord;
        this.type = type;
    }
}
