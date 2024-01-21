package org.example.Models;

public class Gebruiker implements iGebruiker {

    protected String gebruikersnaam, wachtwoord, type;

    public String getGebruikersnaam() {
        return gebruikersnaam;
    }

    public String getType() {
        return type;
    }

}
