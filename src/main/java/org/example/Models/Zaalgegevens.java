package org.example.Models;

public class Zaalgegevens {
    private String zaalnaam, telefoonnummer, website;

    public Zaalgegevens(String zaalnaam, String telefoonnummer, String website){
        this.zaalnaam = zaalnaam;
        this.telefoonnummer = telefoonnummer;
        this.website = website;
    }

    public String getZaalgegevens(){
        return this.zaalnaam + " " + this.telefoonnummer + " " + this.website;
    }
}
