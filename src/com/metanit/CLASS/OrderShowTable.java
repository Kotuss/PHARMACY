package com.metanit.CLASS;

import java.sql.Time;

public class OrderShowTable  {

    private int Id_Order;
    private String Order_Name;
    private int  Order_Amount;
    private String  Order_Status;
    private String Order_Data;
    private String Preson;
    private int IdItems;

    public OrderShowTable(int Id_Order, String Order_Name, int  Order_Amount, String Order_Status, String Order_Data, String Preson, int IdItems){
        this.Id_Order = Id_Order;
        this.Order_Amount = Order_Amount;
        this.Order_Name = Order_Name;
        this.Order_Status=Order_Status;
        this.Order_Data=Order_Data;
        this.Preson=Preson;
        this.IdItems=IdItems;
    }
    public int getId_Order() {
        return Id_Order;
    }

    public void setId_Order(int idOreders) {
        this.Id_Order = idOreders;
    }

    public String getOrder_Name() {
        return Order_Name;
    }

    public void setOrder_Name(String orderName) {
        Order_Name = orderName;
    }

    public int getOrder_Amount() {
        return  Order_Amount;
    }

    public void setOrder_Amount(int orderAmount) {
        Order_Amount = orderAmount;
    }

    public String getOrder_Status() {
        return Order_Status;
    }

    public void setOrder_Status(String order_Status) {
        Order_Status = order_Status;
    }

    public String getOrder_Data() {
        return Order_Data;
    }

    public void setOrder_Data(String order_Data) {
        Order_Data = order_Data;
    }
    public String getPreson() {
        return Preson;
    }

    public void setPreson(String preson) {
        Preson = preson;
    }
    public int getIdItems() {
        return IdItems;
    }

    public void setIdItems(int idItems) {
        IdItems = idItems;
    }
}
