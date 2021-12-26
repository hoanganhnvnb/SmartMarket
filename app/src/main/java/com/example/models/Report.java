package com.example.models;

public class Report {
    public int id;
    public int total_price_import;
    public int total_price_sell;
    public int new_cus;
    public int count_nv;

    public Report(int total_price_import, int total_price_sell, int new_cus, int count_nv) {
        this.total_price_import = total_price_import;
        this.total_price_sell = total_price_sell;
        this.new_cus = new_cus;
        this.count_nv = count_nv;
    }
}
