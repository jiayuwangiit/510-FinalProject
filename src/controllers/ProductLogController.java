package controllers;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import models.Product;
import models.ProductLogModel;
import models.ProductModel;

import java.sql.ResultSet;
import java.util.ArrayList;

public class ProductLogController {


    @FXML
    private TableView<ProductLogModel> tableView;
    @FXML
    private TableColumn<ProductLogModel, Number> idColumn;

    @FXML
    private TableColumn<ProductLogModel, String> nameColumn;
    @FXML
    private TableColumn<ProductLogModel, Number> productIdColumn;
    @FXML
    private TableColumn<ProductLogModel, Number> oldStockColumn;
    @FXML
    private TableColumn<ProductLogModel, Number> newStockColumn;
    @FXML
    private TableColumn<ProductLogModel, String> dateColumn;


    @FXML
    private Button queryBtn;

    @FXML
    private TextField keywordText;

    private final ProductLogModel productLogModel;

    public boolean isAdmin = false;

    public ProductLogController() {
        this.productLogModel = new ProductLogModel(0,"",0,0,0,0,"");
    }

    @FXML
    public void initialize(){
        try{
            if(this.idColumn!=null){

                ObservableList<ProductLogModel> logList = FXCollections.observableArrayList();
                this.idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
                this.nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
                this.productIdColumn.setCellValueFactory(cellData -> cellData.getValue().productIdProperty());
                this.oldStockColumn.setCellValueFactory(cellData -> cellData.getValue().oldStockProperty());
                this.newStockColumn.setCellValueFactory(cellData -> cellData.getValue().newStockProperty());
                this.dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
                tableView.setItems(logList);
                tableView.refresh();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void queryLogs(){
        try{
            if(this.idColumn!=null){
                ResultSet result = productLogModel.getProductLogs(Integer.parseInt(this.keywordText.getText()));
                System.out.println("result:"+result);
                if(result==null){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("WARNING");
                    alert.setHeaderText(null);
                    alert.setContentText("oOps! No found any log!");
                    alert.showAndWait();
                    return;
                }
                ArrayList<ProductLogModel> arrayList = new ArrayList<ProductLogModel>();
                while (result.next()){
                    int id = result.getInt("id");
                    String name  = result.getString("name");
                    int order_id = result.getInt("order_id");
                    int product_id = result.getInt("product_id");
                    int old_stock = result.getInt("old_stock");
                    int new_stock = result.getInt("new_stock");
                    String date  = result.getString("dateline");
                    arrayList.add(new ProductLogModel(id,name,order_id,product_id,old_stock,new_stock,date));
                }
                ObservableList<ProductLogModel> logList = FXCollections.observableArrayList(arrayList);
                this.idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
                this.nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
                this.productIdColumn.setCellValueFactory(cellData -> cellData.getValue().productIdProperty());
                this.oldStockColumn.setCellValueFactory(cellData -> cellData.getValue().oldStockProperty());
                this.newStockColumn.setCellValueFactory(cellData -> cellData.getValue().newStockProperty());
                this.dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
                tableView.setItems(logList);
                tableView.refresh();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void returnMainPage(){
        System.out.println("is admin:"+isAdmin);
        try {
            AnchorPane root;
            if (isAdmin) {
                // If user is admin, inflate admin view
                root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/AdminView.fxml"));
                Main.stage.setTitle("Product Stock Manage System - Admin View - JiaYu Wang");
            } else {
                // If user is customer, inflate customer view
                root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/ClientView.fxml"));
                Main.stage.setTitle("Product Stock Manage System - Client View - JiaYu Wang");
            }
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/assets/main.css").toExternalForm());
            Main.stage.setScene(scene);
        } catch (Exception e) {
            System.out.println("Error occured while inflating view: " + e);
        }
    }
}
