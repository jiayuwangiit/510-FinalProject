package models;

import dao.DBConnect;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductLogModel extends DBConnect {
    private final SimpleIntegerProperty id;

    private final SimpleIntegerProperty order_id;

    private final SimpleIntegerProperty product_id;
    private final SimpleStringProperty name;

    private final SimpleStringProperty date;

    private final SimpleIntegerProperty old_stock;

    private final SimpleIntegerProperty new_stock;


    public ProductLogModel(int id, String name, int order_id,int product_id,int old_stock, int new_stock, String date) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.order_id = new SimpleIntegerProperty(order_id);
        this.product_id = new SimpleIntegerProperty(product_id);
        this.old_stock = new SimpleIntegerProperty(old_stock);
        this.new_stock = new SimpleIntegerProperty(new_stock);
        this.date = new SimpleStringProperty(date);
    }


    public ResultSet getProductLogs(int productId){
         try{
            String sql = "SELECT log.*,pd.name,od.dateline FROM `jiayuw_product_log` log,`jiayuw_orders` od,`jiayuw_products` pd" +
                    " WHERE log.order_id=od.id and log.product_id=pd.id and log.product_id=? order by id desc;";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1,productId);
            String finalSql = stmt.toString();
            System.out.println("Final SQL: " + finalSql);
            return stmt.executeQuery();
        }catch (SQLException exception){
            System.out.println("Sql:"+exception.getStackTrace().toString());
        }
        return null;
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public IntegerProperty productIdProperty() {
        return product_id;
    }

    public IntegerProperty oldStockProperty() {
        return old_stock;
    }

    public IntegerProperty newStockProperty() {
        return new_stock;
    }

    public StringProperty dateProperty() {
        return date;
    }
}
