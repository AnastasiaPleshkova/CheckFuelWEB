package ru.pleshkova.models;

public class FuelRecord {
    private int id;
    private String km;
    private String date;

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
