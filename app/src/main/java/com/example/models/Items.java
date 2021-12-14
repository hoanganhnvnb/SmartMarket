package com.example.models;

public class Items {
    public int id;
    public long barcode;
    public String title;
    public String description;
    public int category;
    public String image;
    public double importPrice;
    public double sellPrice;
    public int quantity;
    public String companyName;
    public boolean active;
    public int quantity_sold;

    public Items() {
        this.id = 0;
        this.barcode = 0;
        this.title = "";
        this.description = "";
        this.category = 0;
        this.image = "";
        this.importPrice = 0;
        this.sellPrice = 0;
        this.quantity = 0;
        this.companyName = "";
        this.active = false;
        this.quantity_sold = 0;
    }

    public Items(int id, long barcode, String title, String description, int category, String image,
                 double importPrice, double sellPrice, int quantity, String companyName, boolean active, int quantity_sold) {
        this.id = id;
        this.barcode = barcode;
        this.title = title;
        this.description = description;
        this.category = category;
        this.image = image;
        this.importPrice = importPrice;
        this.sellPrice = sellPrice;
        this.quantity = quantity;
        this.companyName = companyName;
        this.active = active;
        this.quantity_sold = quantity_sold;
    }
}
