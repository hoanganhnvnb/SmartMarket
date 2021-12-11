package com.example.models;

public class Items {
    public int id;
    public String barcode;
    public String title;
    public String description;
    public Category category;
    public double importPrice;
    public double sellPrice;
    public int quantity;
    public String companyName;
    public boolean active;

    public Items() {
        this.id = 0;
        this.barcode = "";
        this.title = "";
        this.description = "";
        this.category = new Category();
        this.importPrice = 0;
        this.sellPrice = 0;
        this.quantity = 0;
        this.companyName = "";
        this.active = false;
    }

    public Items(int id, String barcode, String title, String description,
                 Category category, double importPrice, double sellPrice,
                 int quantity) {

        this.id = id;
        this.barcode = barcode;
        this.title = title;
        this.description = description;
        this.category = category;
        this.importPrice = importPrice;
        this.sellPrice = sellPrice;
        this.quantity = quantity;
    }
}
