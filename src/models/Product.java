package models;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Product {
    private final SimpleIntegerProperty  id;

    private final SimpleStringProperty name;

    private final SimpleFloatProperty price;

    private final SimpleIntegerProperty  stock;

    public Product(Integer id, String name, Float price,Integer stock) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleFloatProperty(price);
        this.stock = new SimpleIntegerProperty(stock);
    }

    public void setId(Integer id) {
        this.id.set(id);
    }

    public Integer getId() {
        return id.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getName() {
        return name.get();
    }

    public void setPrice(Float price) {
        this.price.set(price);
    }

    public Float getPrice() {
        return price.get();
    }

    public void setStock(Integer stock) {
        this.stock.set(stock);
    }

    public Integer getStock() {
        return stock.get();
    }
}
