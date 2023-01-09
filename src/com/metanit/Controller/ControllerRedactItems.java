package com.metanit.Controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.metanit.CLASS.ItemAddToTable;
import com.metanit.CLASS.ItemShowTable;
import com.metanit.CLASS.UserLOGIN;
import com.metanit.DATABASE.DatabaseHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ControllerRedactItems {

    @FXML
    private Button ButtonClose;

    @FXML
    private TextField AdditionRedact;

    @FXML
    private Button ButtonOK;

    @FXML
    private Button ButtonCancel;

    @FXML
    private TextField CondotionRedact;

    @FXML
    private  Label LableOrders;

    @FXML
    private TextField CountRedact;

    @FXML
    private TextField NameRedact;

    @FXML
    private TextField PositionRedact;

    @FXML
    private TextField PriceRedact;

    @FXML
    private ToggleButton ReceptNeed;

    @FXML
    private ToggleButton ReceptNotNeed;

    @FXML
    void initialize() {
        //кнопка ОК
        ButtonOK.setOnAction(actionEvent -> {
            LableOrders.setText("");
            String NameItem = NameRedact.getText().trim();
            String CostItem = PriceRedact.getText().trim();
            String Possion = PositionRedact.getText().trim();
            String Condition = CondotionRedact.getText().trim();
            String Addition = AdditionRedact.getText().trim();
            String Amount = CountRedact.getText().trim();
            String Recept = "2";
            if (ReceptNeed.isSelected())
                Recept = "ПОТРІБНО";
            else if (ReceptNotNeed.isSelected())
                Recept = "НЕПОТРІБНО";



            if (CheckFields(NameItem, Amount , CostItem, Condition, Recept) == true) {


                if(Amount=="" || isDigit(Amount) == true ||  Integer.parseInt(Amount) >= 0 ) {

                    DatabaseHandler dbHandler = new DatabaseHandler();
                    ItemShowTable items = new ItemShowTable(null,NameItem, "", "","","","","");
                    ResultSet result = dbHandler.CheckName(items);
                    int counter = 0;
                    while (true) {
                        try {
                            if (!result.next()) break;
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                        counter++;
                    }
                    if (counter == 0) {


                        ItemAddToTable item = new ItemAddToTable(NameItem, Amount, CostItem, Possion, Condition, Recept, Addition);
                        try {
                            dbHandler.AddItems(item);
                        } catch (SQLException | ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        NameRedact.setText("");
                        PriceRedact.setText("");
                        PositionRedact.setText("");
                        CondotionRedact.setText("");
                        AdditionRedact.setText("");
                        CountRedact.setText("");
                        ReceptNeed.setSelected(false);
                        ReceptNotNeed.setSelected(false);
                    }
                    else
                        LableOrders.setText("Дане ім'я вже існує");
                }
                else if(isDigit(Amount) == false )
                    LableOrders.setText("Введіть коректне значення кількості");
            }
        });
        //Кнопка СКАСУВАТИ
        ButtonCancel.setOnAction(actionEvent -> {
            ButtonCancel.getScene().getWindow().hide();
        });
        //кнопка ЗАКРИТТЯ

        ButtonClose.setOnAction(actionEvent -> {
            ButtonCancel.getScene().getWindow().hide();
        });

      //  Виведення інформаці про препарат(ЧИ ПОТРІБНИЙ РЕЦЕПТ)
        ReceptNeed.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(ReceptNeed.isSelected()) {
                if (newValue) {
                    ReceptNeed.setStyle("-fx-background-color: #9fafbf; -fx-background-radius: 20");
                    ReceptNotNeed.setStyle("-fx-background-color:    #d6ebff; -fx-background-radius: 20");
                }
            }
            else{
                ReceptNotNeed.setStyle("-fx-background-color: #d6ebff; -fx-background-radius: 20");
                ReceptNeed.setStyle("-fx-background-color:   #d6ebff; -fx-background-radius: 20");
            }
        });
        ReceptNotNeed.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(ReceptNotNeed.isSelected()) {
                if (newValue) {
                    ReceptNotNeed.setStyle("-fx-background-color: #9fafbf; -fx-background-radius: 20");
                    ReceptNeed.setStyle("-fx-background-color:   #d6ebff; -fx-background-radius: 20");
                }
            }
            else{
                ReceptNotNeed.setStyle("-fx-background-color: #d6ebff; -fx-background-radius: 20");
                ReceptNeed.setStyle("-fx-background-color:   #d6ebff; -fx-background-radius: 20");
            }
        });





    }
    //перевірка введення значень
    public  boolean CheckFields(String NameItem, String CountItem,String CostItem, String Condition, String Recept) {
        if (NameItem == "" && Condition == "" && Recept == "2" && CostItem == "" || NameItem == "" && Condition == "" && Recept == "2" ||
                NameItem == "" && Condition == "" && CostItem == "" || Condition == "" && Recept == "2" && CostItem == "" ||
                (NameItem == "" && Recept == "2" && CostItem == "")){
            LableOrders.setText("Введіть значення");
            return false;}
        else if (NameItem == "" && CostItem == ""){
            LableOrders.setText("Введіть ім'я та ціну");
            return false;}
        else if (NameItem == "" && Condition == ""){
            LableOrders.setText("Введіть ім'я та умови зберігання");
            return false;}
        else if (NameItem == "" && Recept == "2"){
            LableOrders.setText("Введіть ім'я та потрібність рецепту");
            return false;}
        else if (CostItem == "" && Condition == ""){
            LableOrders.setText("Введіть ціну та умови");
            return false;}
        else if (CostItem == "" && Recept == "2"){
            LableOrders.setText("Введіть ціну та потрібність рецепту");
            return false;}
        else if (Condition == "" && Recept == "2"){
            LableOrders.setText("Введіть умови та потрібність рецепту");
            return false;}
        else if (NameItem == ""){
            LableOrders.setText("Введіть ім'я");
            return false;}
        else if (CostItem == ""){
            LableOrders.setText("Введіть ціну");
            return false;}
        else if (Condition == ""){
            LableOrders.setText("Введіть умови");
            return false;}
        else if (Recept == "2"){
            LableOrders.setText("Введіть потрібність рецепту");
            return false;}
        else {
            if (isDigit(CostItem) == true && (isDigit(CountItem) == true && Float.valueOf(CountItem) >= 0 && Float.valueOf(CostItem) >= 0))
                return true;

            else if((isDigit(CostItem) == false && isDigit(CountItem) == false) || (Float.valueOf(CostItem) < 0 && Float.valueOf(CountItem) < 0) ) {
                LableOrders.setText("Введіть вірне значення кількості та ціни");
                return false;
            }
            else if (isDigit(CostItem) == false || Float.valueOf(CostItem) < 0) {
                LableOrders.setText("Введіть вірне значення ціни");
                return false;
            } else  {
                LableOrders.setText("Введіть вірне значення кількості");
                return false;
            }
        }
    }
    //перевірка чи значення є числом
    static boolean isDigit(String s) throws NumberFormatException {
        try {
            Float.parseFloat(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }





}
