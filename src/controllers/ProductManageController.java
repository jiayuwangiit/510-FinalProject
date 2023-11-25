package controllers;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import models.ProductModel;

public class ProductManageController {


    @FXML
    private TextField nameText;

    @FXML
    private TextField priceText;

    @FXML
    private TextField stockText;


    private ProductModel productModel;


    public ProductManageController() {
        productModel = new ProductModel();
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
