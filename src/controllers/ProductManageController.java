package controllers;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import models.Product;
import models.ProductModel;

public class ProductManageController {


    @FXML
    private TextField nameText;

    @FXML
    private TextField priceText;

    @FXML
    private TextField stockText;

    @FXML
    private TableView<Product> productsTableView;

    @FXML
    private TableColumn<Product, Number> idColumn;

    @FXML
    private TableColumn<Product, String> nameColumn;

    @FXML
    private TableColumn<Product, Number> priceColumn;

    @FXML
    private TableColumn<Product, Number> stockColumn;

    @FXML
    private Button addBtn;
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;



    private final ProductModel productModel;

    public String page;

    public Product selectedProduct;


    public ProductManageController() {

        this.productModel = new ProductModel();
    }

    @FXML
    public void initialize(){
        try{

            if(this.idColumn!=null){
                ObservableList<Product> tableProductList = FXCollections.observableArrayList(productModel.getProducts());
                this.idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
                this.nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
                this.priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
                this.stockColumn.setCellValueFactory(cellData -> cellData.getValue().stockProperty());

                productsTableView.setItems(tableProductList);
                productsTableView.refresh();
            }

        }catch (Exception exception){
            System.out.println(exception.toString());
            System.out.println("Error:"+exception.getMessage());
        }
    }

    public void deleteSelectRowRow(){
        Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
        if(selectedProduct!=null){
            productModel.deleteProduct(selectedProduct.getId());
            productsTableView.getItems().remove(selectedProduct);
        }else{
            this.alert("Please select product first!");
        }

    }

    public void editProduct(){
        //AnchorPane root;
        try {
            if(productsTableView.getSelectionModel().getSelectedItem()!=null){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddProduct.fxml"));
                Parent root = loader.load();
                Main.stage.setTitle("Add product View");
                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("/assets/main.css").toExternalForm());
                Main.stage.setScene(scene);

                ProductManageController controller = loader.getController();
                controller.selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
                controller.nameText.setText(controller.selectedProduct.getName());
                controller.priceText.setText(controller.selectedProduct.getPrice().toString());
                controller.stockText.setText(controller.selectedProduct.getStock().toString());
            }else{
                this.alert("Please select product first!");
            }

        } catch (Exception e) {
            System.out.println("Error occured while inflating view: " + e);
        }
    }


    public void goToAddProductPage(){
        AnchorPane root;
        try {
            this.page = "add";
            root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/AddProduct.fxml"));
            Main.stage.setTitle("Add product View");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/assets/main.css").toExternalForm());
            Main.stage.setScene(scene);

        } catch (Exception e) {
            System.out.println("Error occured while inflating view: " + e);
        }
    }

    public void goToProductManagePage(){
        AnchorPane root;
        try {
            if(selectedProduct!=null){
                System.out.println("Selected id:"+selectedProduct.getId());
            }
            root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/AdminView.fxml"));
            Main.stage.setTitle("Product Stock Manage System - Admin View - JiaYu Wang");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/assets/main.css").toExternalForm());
            Main.stage.setScene(scene);
        } catch (Exception e) {
            System.out.println("Error occured while inflating view: " + e);
        }
    }


    public void saveProduct(){
        try{
            String name = this.nameText.getText();
            Float price = Float.valueOf(this.priceText.getText());
            Integer stock = Integer.valueOf(this.stockText.getText());
            if(selectedProduct == null){
                productModel.addProduct(name, price, stock);
            }else{
                productModel.updateProduct(selectedProduct.getId(), name, price, stock);
            }
            AnchorPane root;
            root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/AdminView.fxml"));
            Main.stage.setTitle("Admin View");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/assets/main.css").toExternalForm());
            Main.stage.setScene(scene);
        }catch (Exception e){
            System.out.println("Error: " + e);
        }
    }

    public void goToProductLogPage(){

        try {
            if(selectedProduct!=null){
                System.out.println("Selected id:"+selectedProduct.getId());
            }


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ProductLog.fxml"));
            Parent root = loader.load();
            Main.stage.setTitle("Product Stock Manage System - Product Log View - JiaYu Wang");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/assets/main.css").toExternalForm());
            Main.stage.setScene(scene);

            ProductLogController controller = loader.getController();
            controller.isAdmin = true;

        } catch (Exception e) {
            System.out.println("Error occured while inflating view: " + e);
        }
    }

    private void alert(String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("WARNING");
        alert.setHeaderText(null);
        alert.setContentText("oOps! "+message);
        alert.showAndWait();
    }
}
