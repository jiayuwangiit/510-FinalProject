package models;

import javafx.beans.property.*;

public class Product {
    private final SimpleIntegerProperty  id;

    private final SimpleStringProperty name;

    private final SimpleFloatProperty price;

    private final SimpleIntegerProperty stock;

    private final SimpleBooleanProperty select;

    public Product(Integer id, String name, Float price,Integer stock) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleFloatProperty(price);
        this.stock = new SimpleIntegerProperty(stock);
        this.select = new SimpleBooleanProperty(false);
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

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty nameProperty() {
        return name;
    }
    public FloatProperty priceProperty() {
        return price;
    }
    public IntegerProperty stockProperty() {
        return stock;
    }

    public Boolean isSelected(){
        return this.select.getValue();
    }

    public void setSelected(Boolean newValue){
        this.select.set(newValue);
    }
}
