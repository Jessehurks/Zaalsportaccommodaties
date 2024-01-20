package org.example.Models;

public class Zaal {
    private String zaalnaam, accommodatie;

    public Zaal(String zaalnaam, String accommodatie){
        this.zaalnaam = zaalnaam;
        this.accommodatie = accommodatie;
    }

    public String getZaal(){
        return this.zaalnaam + " " + this.accommodatie;
    }
}
