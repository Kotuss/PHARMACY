package com.metanit.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Error {

        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private Button OKERROR;

        @FXML
        void initialize() {
           OKERROR.setOnAction(actionEvent -> {
               OKERROR.getScene().getWindow().hide();
           });

        }

    }
