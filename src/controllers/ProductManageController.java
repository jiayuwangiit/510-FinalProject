package controllers;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import models.Product;
import models.ProductModel;

import java.util.ArrayList;

public class ProductManageController {


    @FXML
    private TextField nameText;

    @FXML
    private TextField priceText;

    @FXML
    private TextField stockText;

    @FXML
    private TableView tableView;

    @FXML
    private TableColumn<ProductModel, Integer> idColumn;

    @FXML
    private TableColumn<ProductModel, String> nameColumn;

    @FXML
    private TableColumn<ProductModel, Integer> priceColumn;

    @FXML
    private TableColumn<ProductModel, Integer> stockColumn;

    private final ProductModel productModel;


    public ProductManageController() {
        productModel = new ProductModel();

        ArrayList<Product> productList = productModel.getProducts();
        System.out.println(productList);
    }
    public void goToAddProductPage(){
        AnchorPane root;
        try {
            root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/AddProduct.fxml"));
            Main.stage.setTitle("Add product View");
            Scene scene = new Scene(root);
            Main.stage.setScene(scene);
        } catch (Exception e) {
            System.out.println("Error occured while inflating view: " + e);
        }
    }

    public void goToProductManagePage(){
        AnchorPane root;
        try {
            ProductModel model = new ProductModel();
            ArrayList<Product> productList = model.getProducts();
System.out.println(productList);
            ObservableList<Product> list = FXCollections.observableArrayList();
            list = FXCollections.observableArrayList(productList);


            tableView.setItems(list);

            root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/AdminView.fxml"));
            Main.stage.setTitle("Admin View");
            Scene scene = new Scene(root);
            Main.stage.setScene(scene);


        } catch (Exception e) {
            System.out.println("Error occured while inflating view: " + e);
        }
    }


    public void createProduct(){
        try{
            String name = this.nameText.getText();
            Float price = Float.valueOf(this.priceText.getText());
            Integer stock = Integer.valueOf(this.stockText.getText());
            productModel.addProduct(name, price, stock);

            AnchorPane root;
            root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/AdminView.fxml"));
            Main.stage.setTitle("Admin View");
            Scene scene = new Scene(root);
            Main.stage.setScene(scene);
        }catch (Exception e){
            System.out.println("Error: " + e);
        }
    }




}
