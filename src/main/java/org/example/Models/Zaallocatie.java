package org.example.Models;

public class Zaallocatie {
    private String zaalnaam, adres, postcode, woonplaats, xkoord, ykoord;

    public Zaallocatie(String zaalnaam, String adres, String postcode, String woonplaats, String xkoord, String ykoord){
        this.zaalnaam = zaalnaam;
        this.adres = adres;
        this.postcode = postcode;
        this.woonplaats = woonplaats;
        this.xkoord = xkoord;
        this.ykoord = ykoord;
    }

    public String getZaallocatie(){
        return this.zaalnaam + " " + this.adres + " " + this.postcode + " " + this.woonplaats + " " + this.xkoord + " " + this.ykoord;
    }
}
