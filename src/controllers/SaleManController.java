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

public class SaleManController {


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
    private TableView<Product> saleTableView;

    @FXML
    private TableColumn<Product, Number> saleIdColumn;

    @FXML
    private TableColumn<Product, String> saleNameColumn;

    @FXML
    private TableColumn<Product, Number> salePriceColumn;

    @FXML
    private Button addBtn;

    @FXML
    private Button restBtn;

    @FXML
    private TextField totalAmountText;



    private final ProductModel productModel;

    public String page;

    public Product selectedProduct;

    private Double totalAmount;

    public ObservableList<Product> selectedProductList = FXCollections.observableArrayList();


    public SaleManController() {
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

                this.saleNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
                this.salePriceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
                saleTableView.setItems(selectedProductList);
                saleTableView.refresh();
            }
        }catch (Exception exception){
            System.out.println(exception.toString());
            System.out.println("Error:"+exception.getMessage());
        }
    }

    public void addSelectedProduct(){
        Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            this.selectedProductList.add(selectedProduct);
            saleTableView.setItems(selectedProductList);
            saleTableView.refresh();
            double sum = this.selectedProductList.stream()
                    .mapToDouble(Product::getPrice)
                    .sum();
            this.totalAmountText.setText(String.valueOf(sum));
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setHeaderText(null);
            alert.setContentText("oOps! Please select a product first!");
            alert.showAndWait();
        }

    }

    public void restProducts(){
        if(!this.selectedProductList.isEmpty()){
            this.selectedProductList.clear();
            saleTableView.setItems(selectedProductList);
            saleTableView.refresh();
            this.totalAmountText.setText("0");
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setHeaderText(null);
            alert.setContentText("oOps! Product list is null!");
            alert.showAndWait();
        }
    }


    public void checkoutNow(){
        if(!this.selectedProductList.isEmpty()){

            productModel.createOrder(selectedProductList);

            this.selectedProductList.clear();
            saleTableView.setItems(selectedProductList);
            saleTableView.refresh();
            this.totalAmountText.setText("0");

            ObservableList<Product> tableProductList = FXCollections.observableArrayList(productModel.getProducts());
            productsTableView.setItems(tableProductList);
            productsTableView.refresh();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("WARNING");
            alert.setHeaderText(null);
            alert.setContentText("Great! Order created successful!");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setHeaderText(null);
            alert.setContentText("oOps! Product list is null!");
            alert.showAndWait();
        }
    }






}
