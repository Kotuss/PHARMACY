package com.metanit.Controller;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

import com.metanit.CLASS.ItemShowTable;
import com.metanit.CLASS.OrderShowTable;
import com.metanit.DATABASE.Constant;
import com.metanit.DATABASE.DatabaseHandler;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class ControllerSell {
    private static  String NAMEPERSONCHECK = null ;
    private static String AMOUNTCHECK = null;
    private static String NAMECHECK = null;
    private static String PRICECHECK = null;


    @FXML
    private Button ButtonClose;

    @FXML
    private Button ButExitSeller;

    @FXML
    private Button ButOrderSeller;

    @FXML
    private Button ButSearchSeller;

    @FXML
    private Button ButStoregSeller;

    @FXML
    private Button ButtonDeleteOrder;

    @FXML
    private Button ButtonRedactOrder;

    @FXML
    private TableColumn<ItemShowTable, String> ColumnSellerICONDITIONS;

    @FXML
    private TableColumn<ItemShowTable, String> ColumnSellerICOST;

    @FXML
    private TableColumn<ItemShowTable, String> ColumnSellerICOUNT;

    @FXML
    private TableColumn<ItemShowTable, String> ColumnSellerIEDITIONS;

    @FXML
    private TableColumn<ItemShowTable, String> ColumnSellerIID;

    @FXML
    private TableColumn<ItemShowTable, String> ColumnSellerINAME;

    @FXML
    private TableColumn<ItemShowTable, String> ColumnSellerIPOSITION;

    @FXML
    private TableColumn<ItemShowTable, String> ColumnSellerIRECEPT;

    @FXML
    private TableColumn<OrderShowTable, Integer> ColumnAmountOrderSeller;

    @FXML
    private TableColumn<OrderShowTable, String> ColumnNameOrderSeller;
    @FXML
    private TableColumn<OrderShowTable, Integer> ColunIDOrderSeller;

    @FXML
    private TableColumn<OrderShowTable, String> ColumnStatusOrderSeller;
    @FXML
    private TableColumn<OrderShowTable, String> ColumnDataOrderSeller;

    @FXML
    private TableView<OrderShowTable> TableOrderSeller;

    @FXML
    private Button ComboButtoneAdd;

    @FXML
    private Button ComboButtoneOrder;

    @FXML
    private TextField ComboFieldAmountOrder;

    @FXML
    private TextField ComboFieldAmountStorage;

    @FXML
    private TextField FieldSearch;

    @FXML
    private Label Lable;

    @FXML
    private Label NAME;

    @FXML
    private TextField NameItemOrder;

    @FXML
    private TextField NameItemStoreg;

    @FXML
    private Label PROFEESSION;
    @FXML
    private Label TIME;

    @FXML
    private Pane PaneAmoutAdd;

    @FXML
    private Pane PaneOrder;

    @FXML
    private Label PoleObOshsbke;

    @FXML
    private Label PoleObOshsbke1;

    @FXML
    private TableView<ItemShowTable> Table;

    int IDitems,IDorder, IDItem,IDORDER;
    private int minute, hour, second, day, month, year;

    String  NameItem,PossionItem,AdditionItem,Name_Order, Status,  amoutIS,  AmountEnter;
    private double xOffset;
    private double yOffset;

    LoginController LOGIN;
    {        LOGIN = new LoginController();    }
    DatabaseHandler dbHandler;
    {        dbHandler = new DatabaseHandler();    }





    @FXML
    public void initialize ()  {
        //Відображення таблиці та ініціалізаці вибору
        Show();
        Timenow();
        Table.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));

        TableOrderSeller.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showOrderDetails(newValue));

        //Виведення інформації про користувача
        ArrayList<String> list =
        list = LOGIN.Send();
        PROFEESSION.setText(list.get(1));
        NAME.setText(list.get(0));

//Кнопка ЗАМОВЛЕННЯ
    ButOrderSeller.setOnAction(actionEvent -> {
        Table.getItems().clear();
        PaneOrder.setVisible(false);
        PaneAmoutAdd.setVisible(true);
        FieldSearch.setText("");
        NameItemStoreg.setText("");
        ComboFieldAmountStorage.setText("");
        PoleObOshsbke.setText("");
        Lable.setText("");
        ShowOrderSeller();


    });
//Редактування замолення
    ButtonRedactOrder.setOnAction(actionEvent -> {
        String AmountEnterr = ComboFieldAmountOrder.getText().trim();
        String Name = NameItemOrder.getText().trim();
        if(Status.equals("НЕВИКОНАНО")) {
            if (Name == "" && (isDigit(AmountEnterr) == false || Integer.parseInt(AmountEnterr) < 0))
                PoleObOshsbke.setText("Введіть коректне значення кількості та виберіть замовлення");
            else if (Name == "")
                PoleObOshsbke.setText("Виберіть замовлення");
            else if (isDigit(AmountEnterr) == false || Integer.parseInt(AmountEnterr) < 0)
                PoleObOshsbke.setText("Введіть коректне значення кількості");
            else {

                try {
                    OrderShowTable order = new OrderShowTable(IDorder, "", Integer.parseInt(AmountEnterr), "", TIME.getText(), "", IDitems);
                    dbHandler.UpdateOrderSeller(order);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                ShowOrderSeller();
                NameItemOrder.setText("");
                ComboFieldAmountOrder.setText("");

            }
        }
        else
            PoleObOshsbke.setText("Це замовлення вже виконано");
        TableOrderSeller.getSelectionModel().clearSelection();
    });
//Видалення замолення
    ButtonDeleteOrder.setOnAction(actionEvent -> {
        if(NameItemOrder.getText()!="") {
            if(Status.equals("НЕВИКОНАНО")) {
                try {
                    dbHandler.DeleteOrder((IDorder));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                NameItemOrder.setText("");
                ComboFieldAmountOrder.setText("");
                ShowOrderSeller();
            }
            else

                PoleObOshsbke.setText("Це замовлення вже виконано");
        }
        else
            PoleObOshsbke.setText("Виберіть препарат");
    });



//кнопка СКЛАД
    ButStoregSeller.setOnAction(actionEvent -> {
        TableOrderSeller.getItems().clear();
        PaneAmoutAdd.setVisible(false);
        PaneOrder.setVisible(true);
        FieldSearch.setText("");
        PoleObOshsbke.setText("");
        Lable.setText("");

        Show();

    });



//створення замолення
        ComboButtoneOrder.setOnAction(actionEvent -> {
            String AmountEnter = ComboFieldAmountStorage.getText().trim();
            String Name = NameItemStoreg.getText().trim();
            Lable.setText("");
            try {
                if (Name == "" && ( AmountEnter==""||isDigit(AmountEnter)==false|| Integer.parseInt(AmountEnter)<0))
                    Lable.setText("Введіть коректне значення кількості та виберіть препарат");
                else if (Name == "")
                    Lable.setText("Виберіть препарат");
                else if (AmountEnter==""||isDigit(AmountEnter)==false||Integer.parseInt(AmountEnter)<0)
                    Lable.setText("Введіть коректне значення кількості");
                else {
                    int AmountEntery = Integer.parseInt(AmountEnter);
                    OrderShowTable order = new OrderShowTable(IDorder ,Name, AmountEntery, "НЕВИКОНАНО",TIME.getText(),  NAME.getText(), IDitems);
                    dbHandler.AddOrder(order);
                    NameItemStoreg.setText("");
                    ComboFieldAmountStorage.setText("");
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            Table.getSelectionModel().clearSelection();
        });


//продати препарат
        ComboButtoneAdd.setOnAction(actionEvent -> {
            AmountEnter = ComboFieldAmountStorage.getText().trim();
            String Name = NameItemStoreg.getText().trim();
            this.NAMEPERSONCHECK = NAME.getText();
            this.AMOUNTCHECK = AmountEnter;

            Lable.setText("");

            if(Float.parseFloat(amoutIS) - Integer.parseInt(AmountEnter) < 0 )
                Lable.setText("Немає необхідної кількості");
            else if (Name == "" && ( isDigit(AmountEnter)==false|| Integer.parseInt(AmountEnter)<0))
                Lable.setText("Введіть коректне значення кількості та виберіть препарат");
            else if (Name == "")
                Lable.setText("Виберіть препарат");
            else if (isDigit(AmountEnter)==false||Integer.parseInt(AmountEnter)<0)
                Lable.setText("Введіть коректне значення кількості");
            else{
                try {


                    int AmountEntery = Integer.parseInt(AmountEnter);

                    dbHandler.SELLItemsSeller(IDitems, AmountEntery);

                    FXMLLoader LoadCHECK = new FXMLLoader(CHECK.class.getResource("CHECK.fxml"));

                    try {
                        LoadCHECK.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    Parent root = LoadCHECK.getRoot();
                    Stage stageCheck = new Stage();
                    Scene scene = new Scene(root, Color.TRANSPARENT);
                    stageCheck.setScene(scene);
                    scene.setOnMousePressed(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            xOffset = stageCheck.getX() - event.getScreenX();
                            yOffset = stageCheck.getY() - event.getScreenY();
                        }
                    });
                    scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            stageCheck.setX(event.getScreenX() + xOffset);
                            stageCheck.setY(event.getScreenY() + yOffset);
                        }
                    });
                    stageCheck.initStyle(StageStyle.UNDECORATED);
                    stageCheck.initStyle(StageStyle.TRANSPARENT);
                    stageCheck.setResizable(false);
                    stageCheck.showAndWait();

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                NameItemStoreg.setText("");
                ComboFieldAmountStorage.setText("");
                Table.getSelectionModel().clearSelection();
                Show();

            }
        });






//вийти з акаунту
        ButExitSeller.setOnAction(actionEvent -> {
        ButExitSeller.getScene().getWindow().hide();

        FXMLLoader loade =new FXMLLoader(LoginController.class.getResource("LoginController.fxml"));

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
        //ПОШУК
        ButSearchSeller.setOnAction(actionEvent -> {
            //пошук по препаратах
            if (PaneOrder.isVisible()) {
                ObservableList<ItemShowTable> listCheck = FXCollections.observableArrayList();
                ResultSet resSet = dbHandler.getItemToTableSearch();
                while (true) {
                    try {

                        try {
                            if (!resSet.next()) break;
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        IDItem = Integer.parseInt(resSet.getString(Constant.ITEM_ID));
                        NameItem = resSet.getString(Constant.ITEM_NAME);
                        PossionItem = resSet.getString(Constant.ITEM_POSITION);
                        AdditionItem = resSet.getString(Constant.ITEM_EDITION);


                        if (IDItem == Integer.parseInt((FieldSearch.getText())))
                            listCheck.add(new ItemShowTable(resSet.getInt(Constant.ITEM_ID), resSet.getString(Constant.ITEM_NAME),
                                    resSet.getString(Constant.ITEM_COUNT), resSet.getString(Constant.ITEM_COST), resSet.getString(Constant.ITEM_POSITION),
                                    resSet.getString(Constant.ITEM_CONDITION), resSet.getString(Constant.ITEM_RECEPT), resSet.getString(Constant.ITEM_EDITION)));

                        else if (NameItem.indexOf(FieldSearch.getText()) > -1)
                            listCheck.add(new ItemShowTable(resSet.getInt(Constant.ITEM_ID), resSet.getString(Constant.ITEM_NAME),
                                    resSet.getString(Constant.ITEM_COUNT), resSet.getString(Constant.ITEM_COST), resSet.getString(Constant.ITEM_POSITION),
                                    resSet.getString(Constant.ITEM_CONDITION), resSet.getString(Constant.ITEM_RECEPT), resSet.getString(Constant.ITEM_EDITION)));

                        else if (PossionItem.indexOf(FieldSearch.getText()) > -1)
                            listCheck.add(new ItemShowTable(resSet.getInt(Constant.ITEM_ID), resSet.getString(Constant.ITEM_NAME),
                                    resSet.getString(Constant.ITEM_COUNT), resSet.getString(Constant.ITEM_COST), resSet.getString(Constant.ITEM_POSITION),
                                    resSet.getString(Constant.ITEM_CONDITION), resSet.getString(Constant.ITEM_RECEPT), resSet.getString(Constant.ITEM_EDITION)));

                        else if (AdditionItem.indexOf(FieldSearch.getText()) > -1)
                            listCheck.add(new ItemShowTable(resSet.getInt(Constant.ITEM_ID), resSet.getString(Constant.ITEM_NAME),
                                    resSet.getString(Constant.ITEM_COUNT), resSet.getString(Constant.ITEM_COST), resSet.getString(Constant.ITEM_POSITION),
                                    resSet.getString(Constant.ITEM_CONDITION), resSet.getString(Constant.ITEM_RECEPT), resSet.getString(Constant.ITEM_EDITION)));

                        SearchItem(listCheck);


                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
            //пошук по замоленнях
            else if (PaneAmoutAdd.isVisible()) {
                ObservableList<OrderShowTable> listCheckOR = FXCollections.observableArrayList();
                ResultSet resSet = dbHandler.OrderSearchSeller();
                while (true) {
                    try {
                        if (!resSet.next()) break;
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        IDORDER = Integer.parseInt(resSet.getString(Constant.ORDER_ID));
                        Name_Order = resSet.getString(Constant.ORDER_NAME);
                        if (IDORDER == Integer.parseInt((FieldSearch.getText())))
                            listCheckOR.add(new OrderShowTable((Integer.parseInt(resSet.getString(Constant.ORDER_ID))),resSet.getString(Constant.ORDER_NAME),
                                    Integer.parseInt(resSet.getString(Constant.ORDER_COUNT)), resSet.getString(Constant.ORDER_STATUS),
                                    resSet.getString(Constant.ORDER_DATA), resSet.getString(Constant.ORDER_PERSON), Integer.parseInt(resSet.getString(Constant.ORDER_ID_ITEM))));

                        else if (Name_Order.indexOf(FieldSearch.getText()) > -1)
                            listCheckOR.add(new OrderShowTable((Integer.parseInt(resSet.getString(Constant.ORDER_ID))),resSet.getString(Constant.ORDER_NAME),
                                    Integer.parseInt(resSet.getString(Constant.ORDER_COUNT)), resSet.getString(Constant.ORDER_STATUS),
                                    resSet.getString(Constant.ORDER_DATA), resSet.getString(Constant.ORDER_PERSON), Integer.parseInt(resSet.getString(Constant.ORDER_ID_ITEM))));

                        SearchOrders(listCheckOR);

                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }


            });
        //закриття програми
            ButtonClose.setOnAction(actionEvent -> {
                System.exit(0);
            });
        }
//дані до чеку
    public ArrayList<String> CHECK(){
        ArrayList<String> list = new ArrayList<String>();
        list.add(NAMEPERSONCHECK);
        list.add(NAMECHECK);
        list.add(AMOUNTCHECK);
        list.add(PRICECHECK);


        return list;

    }
//пошук по таблиці препаратів
        private void SearchItem(ObservableList<ItemShowTable> items){
            ColumnSellerIID.setCellValueFactory(new PropertyValueFactory<ItemShowTable, String>("item_id"));
            ColumnSellerINAME.setCellValueFactory(new PropertyValueFactory<ItemShowTable, String>("item_name"));
            ColumnSellerICOUNT.setCellValueFactory(new PropertyValueFactory<ItemShowTable, String>("item_count"));
            ColumnSellerICOST.setCellValueFactory(new PropertyValueFactory<ItemShowTable, String>("item_cost"));
            ColumnSellerIPOSITION.setCellValueFactory(new PropertyValueFactory<ItemShowTable, String>("item_position"));
            ColumnSellerICONDITIONS.setCellValueFactory(new PropertyValueFactory<ItemShowTable, String>("item_conditions"));
            ColumnSellerIRECEPT.setCellValueFactory(new PropertyValueFactory<ItemShowTable, String>("item_recept"));
            ColumnSellerIEDITIONS.setCellValueFactory(new PropertyValueFactory<ItemShowTable, String>("item_adition"));


            Table.setItems(items);

        }

    //пошук по таблиці замолення
    public void SearchOrders(ObservableList<OrderShowTable> Order) {

        ColunIDOrderSeller.setCellValueFactory(new PropertyValueFactory<OrderShowTable, Integer>("Id_Order"));
        ColumnNameOrderSeller.setCellValueFactory(new PropertyValueFactory<OrderShowTable, String>("Order_Name"));
        ColumnAmountOrderSeller.setCellValueFactory(new PropertyValueFactory<OrderShowTable, Integer>("Order_Amount"));
        ColumnStatusOrderSeller.setCellValueFactory(new PropertyValueFactory<OrderShowTable, String>("Order_Status"));
        ColumnDataOrderSeller.setCellValueFactory(new PropertyValueFactory<OrderShowTable, String>("Order_Data"));

        TableOrderSeller.setItems(Order);
    }


//Виведення таблицы замолення

    public void ShowOrderSeller(){
        ObservableList<OrderShowTable> OrderShowTables;
        ColunIDOrderSeller.setCellValueFactory(new PropertyValueFactory<OrderShowTable, Integer>("Id_Order"));
        ColumnNameOrderSeller.setCellValueFactory(new PropertyValueFactory<OrderShowTable, String>("Order_Name"));
        ColumnAmountOrderSeller.setCellValueFactory(new PropertyValueFactory<OrderShowTable, Integer>("Order_Amount"));
        ColumnStatusOrderSeller.setCellValueFactory(new PropertyValueFactory<OrderShowTable, String>("Order_Status"));
        ColumnDataOrderSeller.setCellValueFactory(new PropertyValueFactory<OrderShowTable, String>("Order_Data"));

            OrderShowTables = dbHandler.getItemToTableViewOrder(NAME.getText());

        TableOrderSeller.setItems(OrderShowTables);

    }
    //Виведення таблицы препаратыв
    public void Show(){
        ObservableList<ItemShowTable> ItemShowTables;
        ColumnSellerIID.setCellValueFactory(new PropertyValueFactory<ItemShowTable, String>("item_id"));
        ColumnSellerINAME.setCellValueFactory(new PropertyValueFactory<ItemShowTable, String>("item_name"));
        ColumnSellerICOUNT.setCellValueFactory(new PropertyValueFactory<ItemShowTable, String>("item_count"));
        ColumnSellerICOST.setCellValueFactory(new PropertyValueFactory<ItemShowTable, String>("item_cost"));
        ColumnSellerIPOSITION.setCellValueFactory(new PropertyValueFactory<ItemShowTable, String>("item_position"));
        ColumnSellerICONDITIONS.setCellValueFactory(new PropertyValueFactory<ItemShowTable, String>("item_conditions"));
        ColumnSellerIRECEPT.setCellValueFactory(new PropertyValueFactory<ItemShowTable, String>("item_recept"));
        ColumnSellerIEDITIONS.setCellValueFactory(new PropertyValueFactory<ItemShowTable, String>("item_adition"));

            ItemShowTables = dbHandler.getItemToTableView();

        Table.setItems(ItemShowTables);

    }

//Вибирі з таблиці препаратів

    private void showPersonDetails(ItemShowTable person) {
        if(person !=null) {
            NameItemStoreg.setText(person.getItem_name());
            IDitems = person.getItem_id();
            NAMECHECK = person.getItem_name();
            amoutIS= person.getItem_count();
            PRICECHECK = person.getItem_cost();


        }
    }
    //Вибирі з таблиці замолення
    private void showOrderDetails(OrderShowTable order) {
        if(order !=null) {
            NameItemOrder.setText(order.getOrder_Name());
            IDorder=order.getId_Order();
            Status= order.getOrder_Status();
        }
    }
    //превірка чи значення є числом
    static boolean isDigit(String s) throws NumberFormatException {
        try {
            Float.parseFloat(String.valueOf(s));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

//Виведення часу
    private void Timenow(){


        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            day = LocalDateTime.now().getDayOfMonth();
            month = LocalDateTime.now().getMonthValue();
            year = LocalDateTime.now().getYear();
            second = LocalDateTime.now().getSecond();
            minute = LocalDateTime.now().getMinute();
            hour = LocalDateTime.now().getHour();
            TIME.setText(day + "-" +month + "-" + year + "   " + hour + ":" + minute + ":" + second);
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

}







