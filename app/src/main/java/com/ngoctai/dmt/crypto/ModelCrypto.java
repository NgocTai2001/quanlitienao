package com.ngoctai.dmt.crypto;


import java.io.Serializable;

public class ModelCrypto implements Serializable {


   private String name;
    private String symbol;
    private double price;
    private double change;
    private String logo;
    private  String id ;

    public ModelCrypto() {
        this.name = name;
        this.symbol = symbol;
        this.price = price;
        this.change = change;
        this.logo = logo;
        this.id = id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
