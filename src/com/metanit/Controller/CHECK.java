package com.metanit.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.metanit.CLASS.ItemShowTable;
import com.metanit.CLASS.OrderShowTable;
import com.metanit.DATABASE.Constant;
import com.metanit.DATABASE.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.swing.*;

public class CHECK {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button BUTDOWLOAD;

    @FXML
    private Button ButtonClose;

    @FXML
    private TextArea TEXT;

    ControllerSell SELL = new ControllerSell();

    String NAME,NAMEOFITEM, Check, PRICE, AMOUNT;
    Float SUM, PDV;
    DatabaseHandler dbHandler;
    {        dbHandler = new DatabaseHandler();    }

    @FXML
    void initialize() {
        ArrayList<String> list = SELL.CHECK();

        NAME = list.get(0);
        NAMEOFITEM = list.get(1);
        AMOUNT = list.get(2);
        PRICE = list.get(3);
        SUM = Float.valueOf(AMOUNT) * Float.valueOf(PRICE);




        Check = "                                    Аптека\n" +
                "                              'ДОБРОГО ДНЯ'\n" +
                "                            смт.Іванків,УКРАЇНА\n" +
                "ПРОДАВЕЦЬ: "+ NAME + "\n" +
                "-------------------------------------------------------------\n" +
                "                             " + NAMEOFITEM +  "\n" +
                "            КІЛЬКІСТЬ: " + AMOUNT +"           |          ЦІНА:      " + PRICE + "\n" +
                "-------------------------------------------------------------\n" +
                "           СУМА:   "  + String.format("%.2f",SUM)+ "            |            ПДВ: " + String.format("%.2f",SUM*0.05) + "\n"+
                "                               СУМА з ПДВ:   "  + String.format("%.2f",SUM*1,05) ;
        TEXT.setText(Check);

//Загрузити ЧЕК
                BUTDOWLOAD.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("C:\\"));

            fileChooser.setTitle("Dowload");
            fileChooser.setInitialFileName("ЧЕК АПТЕКИ");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("text file", ".txt"));


            try {
                File file = fileChooser.showSaveDialog(new Stage());
                if (file != null)
                    seveSystem(file, Check);

            } catch (Exception ex) {

            }


        });
                //закриття
        ButtonClose.setOnAction(actionEvent -> {
            BUTDOWLOAD.getScene().getWindow().hide();
        });


    }

    public void seveSystem(File file, String content) {
        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.write(content);
            printWriter.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
}
