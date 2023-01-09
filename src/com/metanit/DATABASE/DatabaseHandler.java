package com.metanit.DATABASE;

import com.metanit.CLASS.*;
import com.metanit.Controller.ControllerSell;
import com.metanit.Controller.Error;
import com.metanit.Controller.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseHandler extends Configs {
    Connection dbConnection;
    private double xOffset;
    private double yOffset;

    public Connection getDbConnection() {
        try {
            String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;

            Class.forName("com.mysql.cj.jdbc.Driver");

            dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
            Statement st = dbConnection.createStatement();
            st.execute("select 1");

            return dbConnection;
        }
        catch (SQLException | ClassNotFoundException e){

            FXMLLoader loader = new FXMLLoader(Error.class.getResource("Error.fxml"));
            try {
                loader.load();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }


            Parent root = loader.getRoot();
            Stage stage = new Stage();
            Scene scene = new Scene(root, Color.TRANSPARENT);
            stage.setScene(scene);
            scene.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = stage.getX() - event.getScreenX();
                    yOffset = stage.getY() - event.getScreenY();
                }
            });
            scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    stage.setX(event.getScreenX() + xOffset);
                    stage.setY(event.getScreenY() + yOffset);
                }
            });
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setResizable(false);
            stage.showAndWait();
            return null;
        }

    }

    public ResultSet getUser(UserLOGIN user ){
        ResultSet resSet = null;

        String select = "SELECT * FROM " + Constant.USERS_TABLE + " WHERE " +
                Constant.USERS_LOGIN + "=? AND " + Constant.USERS_PASSWORD+ "=? and " + Constant.USERS_PROFISSION + "=?";
        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, user.getLogin());
            prSt.setString(2, user.getPasword());
            prSt.setString(3, user.getProfession());

            resSet=prSt.executeQuery();
        }catch (SQLException e) {

        }
        return resSet;
    }


    public ResultSet CheckName(ItemShowTable items){
        ResultSet resSet = null;

        String select = "SELECT * FROM " + Constant.ITEM_TABLE + " WHERE " +
                Constant.ITEM_NAME + "=?";
        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, items.getItem_name());


            resSet=prSt.executeQuery();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resSet;
    }

    public ResultSet CheckUser(UserLOGIN user){
        ResultSet resSet = null;

        String select = "SELECT * FROM " + Constant.USERS_TABLE + " WHERE " +
                Constant.USERS_LOGIN + "=?";
        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, user.getLogin());


            resSet=prSt.executeQuery();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resSet;
    }

    public void SELLItemsSeller(int ID, float Amount) throws SQLException, ClassNotFoundException {
        String insert = "UPDATE " + Constant.ITEM_TABLE + " SET " + Constant.ITEM_COUNT + "=" + Constant.ITEM_COUNT + "-" + Amount  +" WHERE " + Constant.ITEM_ID + "="  + ID;
        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);

            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void UpdateItemsSeller(int ID, float Amount) throws SQLException, ClassNotFoundException {
        String insert = "UPDATE " + Constant.ITEM_TABLE + " SET " + Constant.ITEM_COUNT + "=" + Constant.ITEM_COUNT + "+" + Amount  +" WHERE " + Constant.ITEM_ID + "="  + ID;
        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);

            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void UpdateOrderSeller(OrderShowTable order) throws SQLException, ClassNotFoundException {
        String insert = "UPDATE " + Constant.ORDER_TABLE + " SET " + Constant.ORDER_COUNT + "=?, " +
                Constant.ORDER_DATA + "=?" +" WHERE " + Constant.ORDER_ID+ "=?";
        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);

            prSt.setString(1, String.valueOf(order.getOrder_Amount()));
            prSt.setString(2, order.getOrder_Data());
            prSt.setInt(3, order.getId_Order());

            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void UpdateOrders(int Id_Order) throws SQLException, ClassNotFoundException {
        String insert = "Update " + Constant.ORDER_TABLE + " SET " + Constant.ORDER_STATUS +"= 'ВИКОНАНО'" + " WHERE " + Constant.ORDER_ID + "=" + Id_Order;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);

            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void UpdateItems(ItemShowTable items) throws SQLException, ClassNotFoundException {
        String insert = "Update " + Constant.ITEM_TABLE + " SET " + Constant.ITEM_COUNT +
                "=?, " + Constant.ITEM_COST + "=?, " + Constant.ITEM_POSITION + "=?, " + Constant.ITEM_CONDITION + "=?, " +
                Constant.ITEM_RECEPT + "=?, " + Constant.ITEM_EDITION + "=?" + " WHERE " + Constant.ITEM_ID + "=" + items.getItem_id();
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);


            prSt.setString(1, items.getItem_count());
            prSt.setString(2, items.getItem_cost());
            prSt.setString(3, items.getItem_position());
            prSt.setString(4, items.getItem_conditions());
            prSt.setString(5, items.getItem_recept());
            prSt.setString(6, items.getItem_adition());

            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void DeleteOrder( int ID) throws SQLException, ClassNotFoundException {
        String insert = "DELETE FROM " + Constant.ORDER_TABLE + " WHERE " + Constant.ORDER_ID + "=" + ID;

        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DeleteWorkers(int ID) throws SQLException, ClassNotFoundException {
        String insert = "DELETE FROM " + Constant.USERS_TABLE + " WHERE " + Constant.USERS_ID + "=" + ID;

        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void DeleteItems( String ID) throws SQLException, ClassNotFoundException {
        String insert = "DELETE FROM " + Constant.ITEM_TABLE + " WHERE " + Constant.ITEM_ID + "=" + ID;

        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void AddOrder(OrderShowTable order) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO " + Constant.ORDER_TABLE + "(" + Constant.ORDER_NAME +"," + Constant.ORDER_COUNT + ","
                + Constant.ORDER_STATUS +"," + Constant.ORDER_DATA +"," + Constant.ORDER_PERSON +"," + Constant.ORDER_ID_ITEM +")" + "VALUES(?,?,?,?,?,?)";
        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, order.getOrder_Name());
            prSt.setString(2, String.valueOf(order.getOrder_Amount()));
            prSt.setString(3, order.getOrder_Status());
            prSt.setString(4, order.getOrder_Data());
            prSt.setString(5, order.getPreson());
            prSt.setString(6, String.valueOf(order.getIdItems()));


            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void AddItems(ItemAddToTable items) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO " + Constant.ITEM_TABLE + "(" + Constant.ITEM_NAME + "," + Constant.ITEM_COUNT +
                ","+ Constant.ITEM_COST + "," + Constant.ITEM_POSITION + ","+ Constant.ITEM_CONDITION + ","+
                Constant.ITEM_RECEPT + ","+ Constant.ITEM_EDITION + ")" + "VALUES(?,?,?,?,?,?,?)";
        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, items.getItem_name());
            prSt.setString(2, items.getItem_count());
            prSt.setString(3, items.getItem_cost());
            prSt.setString(4, items.getItem_position());
            prSt.setString(5, items.getItem_conditions());
            prSt.setString(6, items.getItem_recept());
            prSt.setString(7, items.getItem_adition());

            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void AddPerson(UserLOGIN user) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO " + Constant.USERS_TABLE + "(" + Constant.USERS_LOGIN + "," + Constant.USERS_FIRSTNAME +
                ","+ Constant.USERS_PASSWORD + "," + Constant.USERS_PROFISSION  + ")" + "VALUES(?,?,?,?)";
        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, user.getLogin());
            prSt.setString(2, user.getFirstname());
            prSt.setString(3, user.getPasword());
            prSt.setString(4, user.getProfession());

            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }




    public ObservableList<OrderShowTable> getItemToTableViewOrder(String Name) {
        ObservableList<OrderShowTable> list = FXCollections.observableArrayList();

        String select = "SELECT * FROM " + Constant.ORDER_TABLE + " WHERE " + Constant.ORDER_PERSON + "=" + Name;

        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            ResultSet resSet=prSt.executeQuery();
            while (resSet.next()){
                list.add(new OrderShowTable((Integer.parseInt(resSet.getString(Constant.ORDER_ID))),resSet.getString(Constant.ORDER_NAME),
                        Integer.parseInt(resSet.getString(Constant.ORDER_COUNT)), resSet.getString(Constant.ORDER_STATUS),
                        resSet.getString(Constant.ORDER_DATA), resSet.getString(Constant.ORDER_PERSON), Integer.parseInt(resSet.getString(Constant.ORDER_ID_ITEM))));
            }

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public ObservableList<OrderShowTable> getItemToTableViewOrderAdmin() {
        ObservableList<OrderShowTable> list = FXCollections.observableArrayList();

        String select = "SELECT * FROM " + Constant.ORDER_TABLE ;

        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            ResultSet resSet=prSt.executeQuery();
            while (resSet.next()){
                list.add(new OrderShowTable((Integer.parseInt(resSet.getString(Constant.ORDER_ID))),resSet.getString(Constant.ORDER_NAME),
                        Integer.parseInt(resSet.getString(Constant.ORDER_COUNT)), resSet.getString(Constant.ORDER_STATUS),
                        resSet.getString(Constant.ORDER_DATA), resSet.getString(Constant.ORDER_PERSON), Integer.parseInt(resSet.getString(Constant.ORDER_ID_ITEM))));
            }

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }


    public ObservableList<ItemShowTable> getItemToTableView() {
        ObservableList<ItemShowTable> list = FXCollections.observableArrayList();

        String select = "SELECT * FROM " + Constant.ITEM_TABLE;


        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            ResultSet resSet = prSt.executeQuery();
            while (resSet.next()) {

                list.add(new ItemShowTable(resSet.getInt(Constant.ITEM_ID), resSet.getString(Constant.ITEM_NAME),
                        resSet.getString(Constant.ITEM_COUNT), resSet.getString(Constant.ITEM_COST), resSet.getString(Constant.ITEM_POSITION),
                        resSet.getString(Constant.ITEM_CONDITION),resSet.getString(Constant.ITEM_RECEPT),resSet.getString(Constant.ITEM_EDITION)));
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public ObservableList<User> getWorkerToTableView() {
        ObservableList<User> list = FXCollections.observableArrayList();

        String select = "SELECT * FROM " + Constant.USERS_TABLE;


        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            ResultSet resSet = prSt.executeQuery();
            while (resSet.next()) {

                list.add(new User(Integer.parseInt(resSet.getString(Constant.USERS_ID)), resSet.getString(Constant.USERS_LOGIN),
                        resSet.getString(Constant.USERS_FIRSTNAME), resSet.getString(Constant.USERS_PROFISSION)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public ResultSet getItemToTableSearch() {
        ResultSet resSet = null;

        String select = "SELECT * FROM " + Constant.ITEM_TABLE;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();

        } catch ( SQLException e) {
            throw new RuntimeException(e);
        }
        return resSet;
    }

    public ResultSet getOrderToTableSearch() {
        ResultSet resSet = null;

        String select = "SELECT * FROM " + Constant.ORDER_TABLE ;

        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resSet;
    }

    public ResultSet getWorkersToTableSearch() {
        ResultSet resSet = null;

        String select = "SELECT * FROM " + Constant.USERS_TABLE;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet = prSt.executeQuery();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resSet;
    }
    public ResultSet OrderSearchSeller() {
        ResultSet resSet = null;

        String select = "SELECT * FROM " + Constant.ORDER_TABLE;

        try{
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            resSet=prSt.executeQuery();

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resSet;
    }

}
