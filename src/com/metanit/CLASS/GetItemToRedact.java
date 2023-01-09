package com.metanit.CLASS;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GetItemToRedact {
    private final StringProperty item_id;
    private final StringProperty item_name;
    private final StringProperty item_count;
    private final StringProperty item_cost;
    private final StringProperty item_position;
    private final StringProperty item_conditions;
    private final StringProperty item_recept;
    private final StringProperty item_adition;

    public GetItemToRedact(String item_id, String item_name, String item_count, String item_cost, String item_position, String item_conditions, String item_recept, String item_adition) {
        this.item_id = new SimpleStringProperty(item_id);
        this.item_name = new SimpleStringProperty(item_name);
        this.item_count = new SimpleStringProperty(item_count);
        this.item_cost = new SimpleStringProperty(item_cost);
        this.item_position = new SimpleStringProperty(item_position);
        this.item_conditions = new SimpleStringProperty(item_conditions);
        this.item_recept = new SimpleStringProperty(item_recept);
        this.item_adition = new SimpleStringProperty(item_adition);
    }

    public String getItem_id() {
        return item_id.get();
    }

    public StringProperty item_idProperty() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id.set(item_id);
    }

    public String getItem_name() {
        return item_name.get();
    }

    public StringProperty item_nameProperty() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name.set(item_name);
    }

    public String getItem_count() {
        return item_count.get();
    }

    public StringProperty item_countProperty() {
        return item_count;
    }

    public void setItem_count(String item_count) {
        this.item_count.set(item_count);
    }

    public String getItem_cost() {
        return item_cost.get();
    }

    public StringProperty item_costProperty() {
        return item_cost;
    }

    public void setItem_cost(String item_cost) {
        this.item_cost.set(item_cost);
    }

    public String getItem_position() {
        return item_position.get();
    }

    public StringProperty item_positionProperty() {
        return item_position;
    }

    public void setItem_position(String item_position) {
        this.item_position.set(item_position);
    }

    public String getItem_conditions() {
        return item_conditions.get();
    }

    public StringProperty item_conditionsProperty() {
        return item_conditions;
    }

    public void setItem_conditions(String item_conditions) {
        this.item_conditions.set(item_conditions);
    }

    public String getItem_recept() {
        return item_recept.get();
    }

    public StringProperty item_receptProperty() {
        return item_recept;
    }

    public void setItem_recept(String item_recept) {
        this.item_recept.set(item_recept);
    }

    public String getItem_adition() {
        return item_adition.get();
    }

    public StringProperty item_aditionProperty() {
        return item_adition;
    }

    public void setItem_adition(String item_adition) {
        this.item_adition.set(item_adition);
    }
}


