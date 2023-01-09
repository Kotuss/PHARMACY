package com.metanit.Controller;


import com.metanit.CLASS.*;
import com.metanit.DATABASE.Constant;
import com.metanit.DATABASE.DatabaseHandler;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerAdmin {
    @FXML
    private Button ButAddItem;

    @FXML
    private Button ButtonClose;

    @FXML
    private Button ButDeleteItem;

    @FXML
    private AnchorPane PaneAdmin;

    @FXML
    private ToggleGroup Recept;

    @FXML
    private TextField AdditionRedact1;

    @FXML
    private Button ButExitAdmin;

    @FXML
    private Button ButOrderAdmin;

    @FXML
    private Button ButSearchAdmin;

    @FXML
    private Button ButStorageAdmin;

    @FXML
    private Button ButWorkerAdmin1;

    @FXML
    private Button ButRedactItem;
    @FXML
    private Button ButtonOrdersCancel;
    @FXML
    private Button ButtonOrdersMade;
    @FXML
    private TextField FieldIDorder;
    @FXML
    private Label LableOrderAdmin;
    @FXML
    private TableColumn<ItemShowTable, String> ColumnAdminCONDITIONS;

    @FXML
    private TableColumn<ItemShowTable, String> ColumnAdminCOUNT;

    @FXML
    private TableColumn<ItemShowTable, String> ColumnAdminCost;

    @FXML
    private TableColumn<ItemShowTable, String> ColumnAdminEDITION;

    @FXML
    private TableColumn<ItemShowTable, Integer> ColumnAdminID;

    @FXML
    private TableColumn<ItemShowTable, String> ColumnAdminNAME;

    @FXML
    private TableColumn<ItemShowTable, String> ColumnAdminPOSITION;

    @FXML
    private TableColumn<ItemShowTable, String> ColumnAdminRECEPT;

    @FXML
    private TableColumn<OrderShowTable, String> ColumnAmountOrderAdmin;

    @FXML
    private TableColumn<OrderShowTable, String> ColumnStatusAdmin;

    @FXML
    private TableColumn<OrderShowTable, String> ColumnPersonAdmin;

    @FXML
    private TableColumn<User, String> ColumnIdAddWorkers;

    @FXML
    private TableColumn<User, String> ColumnLoginAddWorkers;
    @FXML
    private TableColumn<OrderShowTable, String> ColumnDataAdmin;

    @FXML
    private TableColumn<User, String> ColumnNameAddWorkers;

    @FXML
    private TableColumn<OrderShowTable, String> ColumnNameOrderAdmin;

    @FXML
    private TableColumn<User, String> ColumnProfessionAddWorkers;

    @FXML
    private TableColumn<OrderShowTable, Integer> ColunIDOrderAdmin;
    @FXML
    private TextField FieldSearchAdmin;

    @FXML
    private Label LableOrders;

    @FXML
    private Pane PaneOrderAdmin;

    @FXML
    private Pane PaneStoregAdmin;

    @FXML
    private Pane PaneWorkersAdmin;
    @FXML
    private TextField CondotionRedact1;
    @FXML
    private TextField NameRedact1;
    @FXML
    private TextField AmountRedact;

    @FXML
    private TextField PositionRedact;

    @FXML
    private TextField PriceRedact;

    @FXML
    private ToggleButton ReceptNeed;

    @FXML
    private ToggleButton ReceptNotNeed;

    @FXML
    private TextField FieldIDWOrker;

    @FXML
    private Button ButtonAddWorkers;

    @FXML
    private Button ButtonDeleteWorkers;

    @FXML
    private Label LableAddWorkers;

    @FXML
    private Label TIME;
    @FXML
    private Label NAME;

    @FXML
    private Label PROFEESSION;

    @FXML
    private ProgressIndicator LOADING;

    @FXML
    private TableView<ItemShowTable> Table;

    @FXML
    private TextField IDRedact;

    @FXML
    private TableView<OrderShowTable> TableOrderAdmin;

    @FXML
    private TableView<User> TableWorkers;

    private int minute, hour, second, day, month, year;
    int Amount_Order,ID_Item_Order, Id_Order, IDItem, IDORDER, Id_worker;
    String NameItem, PossionItem, AdditionItem, Name_Order,PersonOrder,Status_Order, LoginCheck, LoginSearch, NameWorker;
    LoginController LOGIN;
    private static double xOffset;
    private static double yOffset;

    {
        LOGIN = new LoginController();
    }

    DatabaseHandler dbHandler;

    {
        dbHandler = new DatabaseHandler();
    }


    @FXML
    void initialize() {


        ShowTable();
        Timenow();
        Table.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showItemDetails(newValue));

        TableWorkers.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
        TableOrderAdmin.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showOrderDetails(newValue));

        //Виведення інформації про користувача
        ArrayList<String> list =
                list = LOGIN.Send();
        PROFEESSION.setText(list.get(1));
        NAME.setText(list.get(0));
        String LOGINENTRE = (list.get(2));


//  кнопка СКЛАД
        ButStorageAdmin.setOnAction(actionEvent -> {
            ShowTable();
            PaneStoregAdmin.setVisible(true);
            PaneOrderAdmin.setVisible(false);
            PaneWorkersAdmin.setVisible(false);
            FieldSearchAdmin.setText("");
            Clear();
        });
//Редагування препарату
        ButRedactItem.setOnAction(actionEvent -> {
            Integer ID_Item = Integer.valueOf(IDRedact.getText());
            String Name_Item = NameRedact1.getText();
            String Amount_Item = AmountRedact.getText();
            String Price_Item = PriceRedact.getText();
            String Position_Item = PositionRedact.getText();
            String Recept_Item = "2";

            if (ReceptNeed.isSelected())
                Recept_Item = "ПОТРІБНО";
            else if (ReceptNotNeed.isSelected())
                Recept_Item = "НЕПОТРІБНО";
            String Condition_Item = CondotionRedact1.getText();
            String Addition_Item = AdditionRedact1.getText();
            if (CheckFields(Name_Item, Amount_Item, Price_Item, Condition_Item, Recept_Item) == true) {

                try {
                    ItemShowTable items = new ItemShowTable(ID_Item, Name_Item, Amount_Item, Price_Item, Position_Item, Condition_Item, Recept_Item, Addition_Item);
                    dbHandler.UpdateItems(items);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                ShowTable();
                Table.getSelectionModel().clearSelection();
                LableOrders.setText("");
                Clear();
            }


        });

//Додання препарату
        ButAddItem.setOnAction(actionEvent -> {
            try {
                Add();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            ShowTable();
        });

//Видалення препарату
        ButDeleteItem.setOnAction(actionEvent -> {
            if (NameRedact1.getText() != "") {
                try {
                    dbHandler.DeleteItems(String.valueOf(IDRedact.getText()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else
                LableOrders.setText("Виберіть препарат");
            Clear();
            ShowTable();

        });


//  кнопка ЗАМОВЛЕННЯ

        ButOrderAdmin.setOnAction(actionEvent -> {
            PaneStoregAdmin.setVisible(false);
            PaneOrderAdmin.setVisible(true);
            PaneWorkersAdmin.setVisible(false);
            FieldSearchAdmin.setText("");
            Clear();
            ShowTableOrders();

        });
//Виконання замовлення
        ButtonOrdersMade.setOnAction(actionEvent -> {
            LableOrderAdmin.setText("");
            if(FieldIDorder.getText() != "") {
                if (Status_Order.equals("ВИКОНАНО"))
                    LableOrderAdmin.setText("Це замовлення вже виконано");
                else if (Status_Order.equals("НЕВИКОНАНО")) {
                    try {
                        dbHandler.UpdateItemsSeller(ID_Item_Order, Amount_Order);
                        dbHandler.UpdateOrders(Id_Order);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    ShowTableOrders();
                    LableOrderAdmin.setText("");
                }
                TableOrderAdmin.getSelectionModel().clearSelection();
            }
            else {
                 LableOrderAdmin.setText("Виберіть замовлення");
            }
                });
//Скасування замолення
        ButtonOrdersCancel.setOnAction(actionEvent -> {
                    LableOrderAdmin.setText("");
                    if(FieldIDorder.getText() != "") {

                        if (Status_Order.equals("ВИКОНАНО"))
                            LableOrderAdmin.setText("Це замовлення вже виконано");
                        else if (Status_Order.equals("НЕВИКОНАНО")) {
                            try {
                                dbHandler.DeleteOrder(Id_Order);
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            } catch (ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                            LableOrderAdmin.setText("");
                        }
                        ShowTableOrders();
                    }
                    else {
                        LableOrderAdmin.setText("Виберіть замовлення");
                    }

        });


//  кнопка ПРАЦІВНИКИ

        ButWorkerAdmin1.setOnAction(actionEvent -> {
            PaneStoregAdmin.setVisible(false);
            PaneOrderAdmin.setVisible(false);
            PaneWorkersAdmin.setVisible(true);
            FieldSearchAdmin.setText("");
            ShowTableWorkers();
            Clear();
        });
//Видалення працівників
        ButtonDeleteWorkers.setOnAction(actionEvent -> {
            LableAddWorkers.setText("");
            if(FieldIDWOrker.getText() != "") {
                if (LoginCheck.equals("Admin") || FieldIDWOrker.equals("1"))
                    LableAddWorkers.setText("Ви не можете видалити цей акаунт, бо акаунт головного адміна");
                else if (LOGINENTRE.equals(LoginCheck))
                    LableAddWorkers.setText("Ви не можете видалити цей акаунт, бо ви зараз в ньому!");
                else {
                    try {
                        dbHandler.DeleteWorkers(Integer.parseInt(FieldIDWOrker.getText()));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
                ShowTableWorkers();
                TableWorkers.getSelectionModel().clearSelection();
            }else {
                LableAddWorkers.setText("Виберіть працівника");
            }

        });
//Додавання працівників
        ButtonAddWorkers.setOnAction(actionEvent -> {
            try {
                AddWorkers();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            ShowTableWorkers();
            TableWorkers.getSelectionModel().clearSelection();

        });

//кнопка ВИЙТИ
        ButExitAdmin.setOnAction(actionEvent -> {
            ButExitAdmin.getScene().getWindow().hide();

            FXMLLoader loade = new FXMLLoader(LoginController.class.getResource("LoginController.fxml"));

            try {
                loade.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Parent root = loade.getRoot();
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
            stage.show();
        });
//кнопка ПОШУК
        ButSearchAdmin.setOnAction(actionEvent -> {
            ObservableList<ItemShowTable> listCheck = FXCollections.observableArrayList();
            ResultSet resSet = dbHandler.getItemToTableSearch();
//пошук по препаратам
            if(PaneStoregAdmin.isVisible()) {
                while (true) {
                    try {

                        try {
                            if (!resSet.next()) break;
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        IDItem = Integer.valueOf(resSet.getString(Constant.ITEM_ID));
                        NameItem = resSet.getString(Constant.ITEM_NAME);
                        PossionItem = resSet.getString(Constant.ITEM_POSITION);
                        AdditionItem = resSet.getString(Constant.ITEM_EDITION);


                        if (isDigit(FieldSearchAdmin.getText()) == true &&  IDItem == (Integer.valueOf(FieldSearchAdmin.getText())))
                            listCheck.add(new ItemShowTable(resSet.getInt(Constant.ITEM_ID), resSet.getString(Constant.ITEM_NAME),
                                    resSet.getString(Constant.ITEM_COUNT), resSet.getString(Constant.ITEM_COST), resSet.getString(Constant.ITEM_POSITION),
                                    resSet.getString(Constant.ITEM_CONDITION), resSet.getString(Constant.ITEM_RECEPT), resSet.getString(Constant.ITEM_EDITION)));

                        else if (NameItem.indexOf(FieldSearchAdmin.getText()) > -1)
                            listCheck.add(new ItemShowTable(resSet.getInt(Constant.ITEM_ID), resSet.getString(Constant.ITEM_NAME),
                                    resSet.getString(Constant.ITEM_COUNT), resSet.getString(Constant.ITEM_COST), resSet.getString(Constant.ITEM_POSITION),
                                    resSet.getString(Constant.ITEM_CONDITION), resSet.getString(Constant.ITEM_RECEPT), resSet.getString(Constant.ITEM_EDITION)));

                        else if (PossionItem.indexOf(FieldSearchAdmin.getText()) > -1)
                            listCheck.add(new ItemShowTable(resSet.getInt(Constant.ITEM_ID), resSet.getString(Constant.ITEM_NAME),
                                    resSet.getString(Constant.ITEM_COUNT), resSet.getString(Constant.ITEM_COST), resSet.getString(Constant.ITEM_POSITION),
                                    resSet.getString(Constant.ITEM_CONDITION), resSet.getString(Constant.ITEM_RECEPT), resSet.getString(Constant.ITEM_EDITION)));

                        else if (AdditionItem.indexOf(FieldSearchAdmin.getText()) > -1)
                            listCheck.add(new ItemShowTable(resSet.getInt(Constant.ITEM_ID), resSet.getString(Constant.ITEM_NAME),
                                    resSet.getString(Constant.ITEM_COUNT), resSet.getString(Constant.ITEM_COST), resSet.getString(Constant.ITEM_POSITION),
                                    resSet.getString(Constant.ITEM_CONDITION), resSet.getString(Constant.ITEM_RECEPT), resSet.getString(Constant.ITEM_EDITION)));

                        SearchItem(listCheck);


                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }


                }
            }
//пошук по замовленням
            else if (PaneOrderAdmin.isVisible()) {
                resSet=null;
                ObservableList<OrderShowTable> listCheckOrder = FXCollections.observableArrayList();
                resSet = dbHandler.getOrderToTableSearch();
                while (true) {
                    try {

                        try {
                            if (!resSet.next()) break;
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        IDORDER = Integer.valueOf(resSet.getString(Constant.ORDER_ID));
                        Name_Order = resSet.getString(Constant.ORDER_NAME);
                        PersonOrder = resSet.getString(Constant.ORDER_PERSON);

                        if (isDigit(FieldSearchAdmin.getText()) == true &&  IDORDER == Integer.valueOf(FieldSearchAdmin.getText()))
                            listCheckOrder.add(new OrderShowTable((Integer.parseInt(resSet.getString(Constant.ORDER_ID))),resSet.getString(Constant.ORDER_NAME),
                                Integer.parseInt(resSet.getString(Constant.ORDER_COUNT)), resSet.getString(Constant.ORDER_STATUS),
                                resSet.getString(Constant.ORDER_DATA), resSet.getString(Constant.ORDER_PERSON), Integer.parseInt(resSet.getString(Constant.ORDER_ID_ITEM))));

                        else if (Name_Order.indexOf(FieldSearchAdmin.getText()) > -1)
                            listCheckOrder.add(new OrderShowTable((Integer.parseInt(resSet.getString(Constant.ORDER_ID))),resSet.getString(Constant.ORDER_NAME),
                                    Integer.parseInt(resSet.getString(Constant.ORDER_COUNT)), resSet.getString(Constant.ORDER_STATUS),
                                    resSet.getString(Constant.ORDER_DATA), resSet.getString(Constant.ORDER_PERSON), Integer.parseInt(resSet.getString(Constant.ORDER_ID_ITEM))));

                        else if (PersonOrder.indexOf(FieldSearchAdmin.getText()) > -1)
                            listCheckOrder.add(new OrderShowTable((Integer.parseInt(resSet.getString(Constant.ORDER_ID))),resSet.getString(Constant.ORDER_NAME),
                                    Integer.parseInt(resSet.getString(Constant.ORDER_COUNT)), resSet.getString(Constant.ORDER_STATUS),
                                    resSet.getString(Constant.ORDER_DATA), resSet.getString(Constant.ORDER_PERSON), Integer.parseInt(resSet.getString(Constant.ORDER_ID_ITEM))));

                        SearchOrders(listCheckOrder);


                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
//пошук по працівникам
            else if (PaneWorkersAdmin.isVisible()) {
                resSet=null;
                ObservableList<User> ListWorker = FXCollections.observableArrayList();
                resSet = dbHandler.getWorkersToTableSearch();
                while (true) {
                    try {

                        try {
                            if (!resSet.next()) break;
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        Id_worker = Integer.valueOf(resSet.getString(Constant.USERS_ID));
                        LoginSearch = resSet.getString(Constant.USERS_LOGIN);
                        NameWorker = resSet.getString(Constant.USERS_FIRSTNAME);

                        if ( isDigit(FieldSearchAdmin.getText()) == true &&  Id_worker == Integer.valueOf(FieldSearchAdmin.getText()))
                            ListWorker.add(new User(resSet.getInt(Constant.USERS_ID), resSet.getString(Constant.USERS_LOGIN),
                                    resSet.getString(Constant.USERS_FIRSTNAME), resSet.getString(Constant.USERS_PROFISSION)));

                        else if (LoginSearch.indexOf(FieldSearchAdmin.getText()) > -1)
                            ListWorker.add(new User(resSet.getInt(Constant.USERS_ID), resSet.getString(Constant.USERS_LOGIN),
                                    resSet.getString(Constant.USERS_FIRSTNAME), resSet.getString(Constant.USERS_PROFISSION)));

                        else if (NameWorker.indexOf(FieldSearchAdmin.getText()) > -1)
                            ListWorker.add(new User(resSet.getInt(Constant.USERS_ID), resSet.getString(Constant.USERS_LOGIN),
                                    resSet.getString(Constant.USERS_FIRSTNAME), resSet.getString(Constant.USERS_PROFISSION)));

                        SearchWorkers(ListWorker);


                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }


            }
            FieldSearchAdmin.setText("");

        });

//кнопка закриття програми
        ButtonClose.setOnAction(actionEvent -> {
            System.exit(0);
        });
//візуальне нажаття на кнопку "РЕЦЕПТ"
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
//візуальне нажаття на кнопку "РЕЦЕПТ"
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
    //Вивдення інформації пошуку по препаратам
    private void SearchItem(ObservableList<ItemShowTable> items){
        ColumnAdminID.setCellValueFactory(new PropertyValueFactory<ItemShowTable, Integer>("item_id"));
        ColumnAdminNAME.setCellValueFactory(new PropertyValueFactory<ItemShowTable, String>("item_name"));
        ColumnAdminCOUNT.setCellValueFactory(new PropertyValueFactory<ItemShowTable, String>("item_count"));
        ColumnAdminCost.setCellValueFactory(new PropertyValueFactory<ItemShowTable, String>("item_cost"));
        ColumnAdminPOSITION.setCellValueFactory(new PropertyValueFactory<ItemShowTable, String>("item_position"));
        ColumnAdminCONDITIONS.setCellValueFactory(new PropertyValueFactory<ItemShowTable, String>("item_conditions"));
        ColumnAdminRECEPT.setCellValueFactory(new PropertyValueFactory<ItemShowTable, String>("item_recept"));
        ColumnAdminEDITION.setCellValueFactory(new PropertyValueFactory<ItemShowTable, String>("item_adition"));


        Table.setItems(items);

    }
    //Вивдення інформації пошуку по працівникам
    public void SearchWorkers(ObservableList<User> User) {
        ColumnIdAddWorkers.setCellValueFactory(new PropertyValueFactory<User, String>("ID"));
        ColumnLoginAddWorkers.setCellValueFactory(new PropertyValueFactory<User, String>("login"));
        ColumnNameAddWorkers.setCellValueFactory(new PropertyValueFactory<User, String>("Firstname"));
        ColumnProfessionAddWorkers.setCellValueFactory(new PropertyValueFactory<User, String>("profession"));

        TableWorkers.setItems(User);
    }
    //Вивдення інформації пошуку по замовленням
    public void SearchOrders(ObservableList<OrderShowTable> Order) {

        ColunIDOrderAdmin.setCellValueFactory(new PropertyValueFactory<OrderShowTable, Integer>("Id_Order"));
        ColumnNameOrderAdmin.setCellValueFactory(new PropertyValueFactory<OrderShowTable, String>("Order_Name"));
        ColumnAmountOrderAdmin.setCellValueFactory(new PropertyValueFactory<OrderShowTable, String>("Order_Amount"));
        ColumnDataAdmin.setCellValueFactory(new PropertyValueFactory<OrderShowTable, String>("Order_Data"));
        ColumnStatusAdmin.setCellValueFactory(new PropertyValueFactory<OrderShowTable, String>("Order_Status"));
        ColumnPersonAdmin.setCellValueFactory(new PropertyValueFactory<OrderShowTable, String>("Preson"));

        TableOrderAdmin.setItems(Order);
    }

    //Вибір інформації про препарат з таблиці
    private void showPersonDetails(User user) {
        if (user != null) {
            Id_worker = user.getID();
            FieldIDWOrker.setText(String.valueOf(Id_worker));
            LoginCheck = user.getLogin();
            LableAddWorkers.setText("");
        }
    }
    //Вибір інформації про замолення з таблиці
    private void showOrderDetails(OrderShowTable order) {
        if (order != null) {
            Id_Order = (order.getId_Order());
            Name_Order = order.getOrder_Name();
            Status_Order = order.getOrder_Status();
            Amount_Order = order.getOrder_Amount();
            ID_Item_Order = order.getIdItems();
            LableOrders.setText("");
        }
    }
    //Вибір інформації про препарат з таблиці
    private void showItemDetails(ItemShowTable person) {
        if (person != null) {
            IDRedact.setText(String.valueOf(person.getItem_id()));
            NameRedact1.setText(person.getItem_name());
            AmountRedact.setText(person.getItem_count());
            PriceRedact.setText(person.getItem_cost());
            PositionRedact.setText(person.getItem_position());
            String recept = person.getItem_recept();

            if (recept.equals("НЕПОТРІБНО")) {
                ReceptNotNeed.setSelected(true);
                ReceptNeed.setSelected(false);
            } else if (recept.equals("ПОТРІБНО")) {
                ReceptNeed.setSelected(true);
                ReceptNotNeed.setSelected(false);
            } else {
                ReceptNotNeed.setSelected(false);
                ReceptNeed.setSelected(false);
            }
            CondotionRedact1.setText(person.getItem_conditions());
            AdditionRedact1.setText(person.getItem_adition());
            LableOrders.setText("");

        }
    }
//перевірка заповненості полів
    public boolean CheckFields(String NameItem,String Amount ,String CostItem, String Condition, String Recept) {
            if (NameItem == "" && Condition == "" && Recept == "2" && CostItem == "" || NameItem == "" && Condition == "" && Recept == "2" ||
                NameItem == "" && Condition == "" && CostItem == "" || Condition == "" && Recept == "2" && CostItem == "" ||
                (NameItem == "" && Recept == "2" && CostItem == "")) {
                LableOrders.setText("Введіть значення");
                return false;
            } else if (NameItem == "" && CostItem == "") {
                LableOrders.setText("Введіть ім'я та ціну");
                return false;
            } else if (NameItem == "" && Condition == "") {
                LableOrders.setText("Введіть ім'я та умови зберігання");
                return false;
            } else if (NameItem == "" && Recept == "2") {
                LableOrders.setText("Введіть ім'я та потрібність рецепту");
                return false;
            } else if (CostItem == "" && Condition == "") {
                LableOrders.setText("Введіть ціну та умови");
                return false;
            } else if (CostItem == "" && Recept == "2") {
                LableOrders.setText("Введіть ціну та потрібність рецепту");
                return false;
            } else if (Condition == "" && Recept == "2") {
                LableOrders.setText("Введіть умови та потрібність рецепту");
                return false;
            } else if (NameItem == "") {
                LableOrders.setText("Введіть ім'я");
                return false;
            } else if (CostItem == "") {
                LableOrders.setText("Введіть ціну");
                return false;
            } else if (Condition == "") {
                LableOrders.setText("Введіть умови");
                return false;
            } else if (Recept == "2") {
                LableOrders.setText("Введіть потрібність рецепту");
                return false;
            } else {

                if (isDigit(CostItem) == true && (isDigit(Amount) == true && Float.valueOf(Amount) >= 0 && Float.valueOf(CostItem) >= 0))
                    return true;

                else if((isDigit(CostItem) == false && isDigit(Amount) == false) || (Float.valueOf(CostItem) < 0 && Float.valueOf(Amount) < 0) ) {
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

    //метод перевірки чи введено число
    static boolean isDigit(String s) throws NumberFormatException {
        try {
            if(Float.parseFloat(s)>=0)
                return true;
            else
                return false;

        } catch (NumberFormatException e) {
            return false;
        }

    }

    //Вивод товарів в таблицю
    public void ShowTable() {
        ObservableList<ItemShowTable> ItemShowTables;
        ColumnAdminID.setCellValueFactory(new PropertyValueFactory<ItemShowTable, Integer>("item_id"));
        ColumnAdminNAME.setCellValueFactory(new PropertyValueFactory<ItemShowTable, String>("item_name"));
        ColumnAdminCOUNT.setCellValueFactory(new PropertyValueFactory<ItemShowTable, String>("item_count"));
        ColumnAdminCost.setCellValueFactory(new PropertyValueFactory<ItemShowTable, String>("item_cost"));
        ColumnAdminPOSITION.setCellValueFactory(new PropertyValueFactory<ItemShowTable, String>("item_position"));
        ColumnAdminCONDITIONS.setCellValueFactory(new PropertyValueFactory<ItemShowTable, String>("item_conditions"));
        ColumnAdminRECEPT.setCellValueFactory(new PropertyValueFactory<ItemShowTable, String>("item_recept"));
        ColumnAdminEDITION.setCellValueFactory(new PropertyValueFactory<ItemShowTable, String>("item_adition"));

        ItemShowTables = dbHandler.getItemToTableView();

        Table.setItems(ItemShowTables);
    }
    //Вивод працівників в таблицю
    public void ShowTableWorkers() {
        ObservableList<User> User;
        ColumnIdAddWorkers.setCellValueFactory(new PropertyValueFactory<User, String>("ID"));
        ColumnLoginAddWorkers.setCellValueFactory(new PropertyValueFactory<User, String>("login"));
        ColumnNameAddWorkers.setCellValueFactory(new PropertyValueFactory<User, String>("Firstname"));
        ColumnProfessionAddWorkers.setCellValueFactory(new PropertyValueFactory<User, String>("profession"));

        User = dbHandler.getWorkerToTableView();

        TableWorkers.setItems(User);
    }
//відкриття вікна додавання прапарату
    public static void Add() throws Exception {

        FXMLLoader loader = new FXMLLoader(ControllerRedactItems.class.getResource("ControllerRedactItems.fxml"));

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

    //відкриття вікна додавання працівника
    public static void AddWorkers() throws Exception {

        FXMLLoader loader = new FXMLLoader(ControllerRedactPerson.class.getResource("ControllerRedactPerson.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root, Color.TRANSPARENT));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.showAndWait();
    }
//очистка полів
    public void Clear() {
        IDRedact.setText("");
        NameRedact1.setText("");
        AmountRedact.setText("");
        PriceRedact.setText("");
        PositionRedact.setText("");
        ReceptNeed.setSelected(false);
        ReceptNotNeed.setSelected(false);
        CondotionRedact1.setText("");
        AdditionRedact1.setText("");
        LableOrderAdmin.setText("");
    }
//виведення часу
    private void Timenow() {


        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            day = LocalDateTime.now().getDayOfMonth();
            month = LocalDateTime.now().getMonthValue();
            year = LocalDateTime.now().getYear();
            second = LocalDateTime.now().getSecond();
            minute = LocalDateTime.now().getMinute();
            hour = LocalDateTime.now().getHour();
            TIME.setText(day + "-" + month + "-" + year + "   " + hour + ":" + minute + ":" + second);
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
//Виведення таблиці замолень
    public void ShowTableOrders() {
        ObservableList<OrderShowTable> Order;
        ColunIDOrderAdmin.setCellValueFactory(new PropertyValueFactory<OrderShowTable, Integer>("Id_Order"));
        ColumnNameOrderAdmin.setCellValueFactory(new PropertyValueFactory<OrderShowTable, String>("Order_Name"));
        ColumnAmountOrderAdmin.setCellValueFactory(new PropertyValueFactory<OrderShowTable, String>("Order_Amount"));
        ColumnDataAdmin.setCellValueFactory(new PropertyValueFactory<OrderShowTable, String>("Order_Data"));
        ColumnStatusAdmin.setCellValueFactory(new PropertyValueFactory<OrderShowTable, String>("Order_Status"));
        ColumnPersonAdmin.setCellValueFactory(new PropertyValueFactory<OrderShowTable, String>("Preson"));


        Order = dbHandler.getItemToTableViewOrderAdmin();

        TableOrderAdmin.setItems(Order);
    }


}
