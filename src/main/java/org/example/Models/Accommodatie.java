package org.example.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Accommodatie {
    @SerializedName("ACCOMMODATIE")
    @Expose
    private String accommodatie;
    public Accommodatie (String accommodatie){
        this.accommodatie = accommodatie;
    }

    public String getAccommodatie(){
        return this.accommodatie;
    }

}
