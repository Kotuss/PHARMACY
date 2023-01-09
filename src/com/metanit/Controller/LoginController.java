package com.metanit.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.metanit.Animation.Shake;
import com.metanit.CLASS.UserLOGIN;

import com.metanit.DATABASE.DatabaseHandler;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class LoginController {

    private static  String LOGIN = null ;
    private static String PROFESSION = null;
    private static String NAME = null;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button ButEnter;

    @FXML
    private ToggleGroup Kindofuser;
    @FXML
    private Button ButtonHide;
    @FXML
    private Button ButtonClose;

    @FXML
    private TextField LogField;

    @FXML
    private PasswordField PaswordField;

    @FXML
    private ToggleButton TogButAdmin;

    @FXML
    private ToggleButton TogButSeller;

    @FXML
    private Label UncorrectLog;

    private double xOffset;
    private double yOffset;



    @FXML
    void initialize() {
        //кнопка входу
        ButEnter.setOnAction(actionEvent -> {
            String loginText = LogField.getText().trim();
            String paswordText = PaswordField.getText().trim();
            UncorrectLog.setText("");
//вибір продавця
            if (TogButSeller.isSelected()) {
                if (!loginText.equals("") && !paswordText.equals("")) {
                    DatabaseHandler dbHandler = new DatabaseHandler();
                    UserLOGIN user = new UserLOGIN(loginText,"", paswordText, "Seller");
                    ResultSet result = dbHandler.getUser(user);

                    int counter = 0;
                    while (true) {
                        try {
                            if (!result.next()) break;
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                        counter++;
                        try {
                             this.NAME = (result.getString("FirstandLastname"));
                            this.PROFESSION ="Seller";
                            this.LOGIN = (result.getString("login"));
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }


                    }

                    if (counter >= 1){

                        ButEnter.getScene().getWindow().hide();

                        FXMLLoader loader = new FXMLLoader(ControllerSell.class.getResource("ControllerSell.fxml"));
                        try {
                            loader.load();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
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

                    }

                    else {
                        UncorrectLog.setText("Введено невірний логін або пароль!");
                        LogField.setText("");
                        PaswordField.setText("");
                        Shake();
                    }

                } else {
                    UncorrectLog.setText("Будь ласка, введіть логін та пароль!");
                    LogField.setText("");
                    PaswordField.setText("");
                    Shake();
                }
            }



//вибір АДМІНСТРАТОРА

            else if (TogButAdmin.isSelected()) {
                if (!loginText.equals("") && !paswordText.equals("")) {
                    DatabaseHandler dbHandler = new DatabaseHandler();
                    UserLOGIN user = new UserLOGIN(loginText,"",paswordText, "Admin");
                    ResultSet result = dbHandler.getUser(user);

                    int counter = 0;
                    while(true) {
                        try {
                            if (!result.next()) break;
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        counter++;
                        try {
                            this.NAME = (result.getString("FirstandLastname"));
                            this.PROFESSION ="Admin";
                            this.LOGIN = (result.getString("login"));
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    if (counter >= 1){
                        ButEnter.getScene().getWindow().hide();

                        FXMLLoader loader = new FXMLLoader(ControllerAdmin.class.getResource("ControllerAdmin.fxml"));
                        try {
                            loader.load();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
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



                    }

                    else {
                        UncorrectLog.setText("Введено невірний логін або пароль!");
                        LogField.setText("");
                        PaswordField.setText("");
                        Shake();
                    }
                } else {
                    UncorrectLog.setText("Будь ласка, введіть логін та пароль!");
                    LogField.setText("");
                    PaswordField.setText("");
                    Shake();
                }
            }

            else {
                UncorrectLog.setText("Виберіть посаду!");
                ShakeProfession();
            }

        });
        //кнопка закриття

        ButtonClose.setOnAction(actionEvent -> {
            System.exit(0);
        });



//Виведення інформації про товар (ЧИ ПОТРІБНИЙ РЕЦЕПТ)

        TogButAdmin.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(TogButAdmin.isSelected()) {
                if (newValue) {
                    TogButAdmin.setStyle("-fx-background-color: #9fafbf; -fx-background-radius: 20");
                    TogButSeller.setStyle("-fx-background-color:    #9fc6ed; -fx-background-radius: 20");
                }
            }
            else{
                TogButSeller.setStyle("-fx-background-color: #9fc6ed; -fx-background-radius: 20");
                TogButAdmin.setStyle("-fx-background-color:   #9fc6ed; -fx-background-radius: 20");
            }
        });
        TogButSeller.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(TogButSeller.isSelected()) {
                if (newValue) {
                    TogButSeller.setStyle("-fx-background-color: #9fafbf; -fx-background-radius: 20");
                    TogButAdmin.setStyle("-fx-background-color:   #9fc6ed; -fx-background-radius: 20");
                }
            }
            else{
                TogButSeller.setStyle("-fx-background-color: #9fc6ed; -fx-background-radius: 20");
                TogButAdmin.setStyle("-fx-background-color:   #9fc6ed; -fx-background-radius: 20");
            }
        });

    }
//інформаці про працівника
    public ArrayList<String> Send(){
        ArrayList<String> list = new ArrayList<String>();;
        list.add(NAME);
        list.add(PROFESSION);
        list.add(LOGIN);

        return list;
    }
    //анімация
    public void Shake(){
        Shake LoginShake = new Shake(LogField);
        Shake PaswordShake = new Shake(PaswordField);
        LoginShake.PlayAnimation();
        PaswordShake.PlayAnimation();
    }
    public void ShakeProfession(){
        Shake Seller = new Shake(TogButSeller);
        Shake Admin = new Shake(TogButAdmin);
        Seller.PlayAnimation();
        Admin.PlayAnimation();
    }

}


