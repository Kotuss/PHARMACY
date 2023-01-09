package com.metanit.Controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


import com.metanit.CLASS.UserLOGIN;
import com.metanit.DATABASE.DatabaseHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ControllerRedactPerson {


    @FXML
    private ToggleGroup AddWorkers;

    @FXML
    private Button ButtonClose;

    @FXML
    private Button ButtonCancel;

    @FXML
    private Button ButtonOK;

    @FXML
    private Label LableOrders;

    @FXML
    private TextField LoginAdd;

    @FXML
    private TextField NameFirstAdd;

    @FXML
    private PasswordField PaswordAdd;

    @FXML
    private PasswordField PaswordAddCheck;

    @FXML
    private ToggleButton ToggelAddWorkersAdmin;

    @FXML
    private ToggleButton ToggelAddWorkersSeller;

    @FXML
    void initialize() {
        ButtonOK.setOnAction(actionEvent -> {
            LableOrders.setText("");
            String Login = LoginAdd.getText().trim();
            String Passwordd = PaswordAdd.getText().trim();
            String PassworddCheck = PaswordAddCheck.getText().trim();
            String Name = NameFirstAdd.getText().trim();
            String Profession = "2";
            if (ToggelAddWorkersSeller.isSelected())
                Profession = "Seller";
            else if (ToggelAddWorkersAdmin.isSelected())
                Profession = "Admin";

        if(Passwordd.equals(PassworddCheck) == true) {

            if (CheckFields(Login, Passwordd, Name, Profession) == true) {

                DatabaseHandler dbHandler = new DatabaseHandler();
                UserLOGIN user = new UserLOGIN( Login, "", "","");
                ResultSet result = dbHandler.CheckUser(user);
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
                    UserLOGIN user1 = new UserLOGIN(Login, Name ,Passwordd, Profession);
                    try {
                        dbHandler.AddPerson(user1);
                    } catch (SQLException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    LoginAdd.setText("");
                    PaswordAdd.setText("");
                    PaswordAddCheck.setText("");
                    NameFirstAdd.setText("");
                    ToggelAddWorkersSeller.setSelected(false);
                    ToggelAddWorkersAdmin.setSelected(false);

                } else
                    LableOrders.setText("Даний ЛОГІН вже існує");
            }
        }
        else
            LableOrders.setText("ПАРОЛІ не співпадають");
        });



        ButtonCancel.setOnAction(actionEvent -> {
            ButtonCancel.getScene().getWindow().hide();
        });
        ButtonClose.setOnAction(actionEvent -> {
            ButtonCancel.getScene().getWindow().hide();
        });


        ToggelAddWorkersAdmin.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(ToggelAddWorkersAdmin.isSelected()) {
                if (newValue) {
                    ToggelAddWorkersAdmin.setStyle("-fx-background-color: #9fafbf; -fx-background-radius: 20");
                    ToggelAddWorkersSeller.setStyle("-fx-background-color:    #d6ebff; -fx-background-radius: 20");
                }
            }
            else{
                ToggelAddWorkersSeller.setStyle("-fx-background-color: #d6ebff; -fx-background-radius: 20");
                ToggelAddWorkersAdmin.setStyle("-fx-background-color:   #d6ebff; -fx-background-radius: 20");
            }
        });
        ToggelAddWorkersSeller.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(ToggelAddWorkersSeller.isSelected()) {
                if (newValue) {
                    ToggelAddWorkersSeller.setStyle("-fx-background-color: #9fafbf; -fx-background-radius: 20");
                    ToggelAddWorkersAdmin.setStyle("-fx-background-color:   #d6ebff; -fx-background-radius: 20");
                }
            }
            else{
                ToggelAddWorkersSeller.setStyle("-fx-background-color: #d6ebff; -fx-background-radius: 20");
                ToggelAddWorkersAdmin.setStyle("-fx-background-color:   #d6ebff; -fx-background-radius: 20");
            }
        });




    }
    public  boolean CheckFields(String Login,String Passwordd, String Name ,String Recept) {
        if (Login == "" && Passwordd == "" && Recept == "2" && Name == "" || Login == "" && Passwordd == "" && Recept == "2" ||
                Login == "" && Passwordd == "" && Name == "" || Passwordd == "" && Recept == "2" && Name == "" ||
                (Login == "" && Recept == "2" && Name == "")){
            LableOrders.setText("Введіть значення");
                return false;}
        else if (Login == "" && Name == ""){
            LableOrders.setText("Введіть ЛОГІН та ІМ'Я");
                return false;}
        else if (Login == "" && Passwordd == ""){
            LableOrders.setText("Введіть ЛОГІН та умови ПАРОЛЬ");
                return false;}
        else if (Login == "" && Recept == "2"){
            LableOrders.setText("Введіть ЛОГІН та ПРОФЕСІЮ");
                return false;}
        else if (Name == "" && Passwordd == ""){
            LableOrders.setText("Введіть ІМ'Я та ПАРОЛЬ");
                return false;}
        else if (Name == "" && Recept == "2"){
            LableOrders.setText("Введіть ІМ'Я та ПРОФЕСІЮ");
                return false;}
        else if (Passwordd == "" && Recept == "2"){
            LableOrders.setText("Введіть ПАРОЛЬ та ПРОФЕСІЮ");
                return false;}
        else if (Login == ""){
            LableOrders.setText("Введіть ЛОГІН");
                return false;}
        else if (Name == ""){
            LableOrders.setText("Введіть ІМ'Я");
                return false;}
        else if (Passwordd == ""){
            LableOrders.setText("Введіть ПАРОЛЬ");
                return false;}
        else if (Recept == "2"){
            LableOrders.setText("Введіть ПРОФЕСІЮ");
                return false;}
        else
            return true;
    }

}
