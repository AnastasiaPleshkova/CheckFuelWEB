package ru.pleshkova.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class FuelRecord {
    private int id;

    @Min(value = 0, message = "Value should be positive")
    private String km;
    @NotEmpty(message = "Date cant be empty")
    private String date;

    public FuelRecord(){

    }

    public FuelRecord(int id, String km, String date) {
        this.id = id;
        this.km = km;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
