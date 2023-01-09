package com.metanit.CLASS;

public class ItemShowTable {

    private  Integer item_id;
    private  String item_name;
    private  String item_count;
    private  String item_cost;
    private  String item_position;
    private  String item_conditions;
    private  String item_recept;
    private  String item_adition;

    public ItemShowTable(Integer item_id, String item_name, String item_count, String item_cost, String item_position, String item_conditions, String item_recept, String item_adition) {
        this.item_id = item_id;
        this.item_name = item_name;
        this.item_count = item_count;
        this.item_cost = item_cost;
        this.item_position = item_position;
        this.item_conditions = item_conditions;
        this.item_recept = item_recept;
        this.item_adition = item_adition;
    }

    public Integer getItem_id() {
        return item_id;
    }

    public void setItem_id(Integer item_id) {
        this.item_id = item_id;
    }

    public  String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public  String getItem_count() {
        return item_count;
    }

    public void setItem_count(String item_count) {
        this.item_count = item_count;
    }

    public  String getItem_cost() {
        return item_cost;
    }

    public void setItem_cost(String item_cost) {
        this.item_cost = item_cost;
    }

    public  String getItem_position() {
        return item_position;
    }

    public void setItem_position(String item_position) {
        this.item_position = item_position;
    }

    public  String getItem_conditions() {
        return item_conditions;
    }

    public void setItem_conditions(String item_conditions) {
        this.item_conditions = item_conditions;
    }

    public  String getItem_recept() { return item_recept;   }

    public void setItem_recept(String item_recept) {this.item_recept = item_recept;    }

    public String getItem_adition() {
        return item_adition;
    }

    public void setItem_adition(String item_adition) {
        this.item_adition = item_adition;
    }
}
