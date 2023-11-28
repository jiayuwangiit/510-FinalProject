package models;

import dao.DBConnect;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class ProductModel extends DBConnect {




    public void addProduct(String name, Float price, Integer stock){
        String sql = "INSERT INTO `jiayuw_products`(`name`, `price`, `stock`) VALUES (?,?,?);";
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setFloat(2, price);
            stmt.setInt(3, stock);
            stmt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProduct(Integer id, String name, Float price, Integer stock){
        String sql = "update `jiayuw_products` set `name`=?,`price`=?,`stock`=? where id=?;";
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setFloat(2, price);
            stmt.setInt(3, stock);
            stmt.setInt(4, id);
            stmt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Product> getProducts(){
        ArrayList<Product> productModels = new ArrayList<Product>();
        try{
            Statement stmt = connection.createStatement();
            String sql = "SELECT * from `jiayuw_products` order by id desc";
            ResultSet resultSet = null;
            resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Float price = resultSet.getFloat("price");
                Integer stock = resultSet.getInt("stock");
                Product product = new Product(id,name,price,stock);

                productModels.add(product);
            }
        }catch (SQLException exception){
            exception.printStackTrace();
        }
        return productModels;
    }

    public Boolean deleteProduct(Integer id)
    {
        String sql = "Delete from `jiayuw_products` where id=? limit 1;";
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean createOrder(ObservableList<Product> products ){
        try{
            String reduceSql = "update `jiayuw_products` set `stock`=`stock`-1 where id=?";

            //Create order
            double sum = products.stream()
                    .mapToDouble(Product::getPrice)
                    .sum();
            String orderSql = "INSERT INTO `jiayuw_orders` set `amount`=?,`dateline`=?";
            PreparedStatement orderStmt = connection.prepareStatement(orderSql);
            orderStmt.setDouble(1, sum);
            orderStmt.setString(2, this.getDatetime());
            orderStmt.executeUpdate();
            //get order id just created
            int orderId = this.getLastOrderId();

            for (Product productItem : products) {
                int id = productItem.getId();



                String query = "SELECT * FROM jiayuw_products WHERE id = ? limit 1;";
                PreparedStatement stmtP = connection.prepareStatement(query);
                stmtP.setInt(1, id);
                ResultSet rs = stmtP.executeQuery();
                if(rs.next()) {




                    int oldStock = rs.getInt("stock");
                    int newStock = oldStock-1;

                    System.out.println("orderId: "+orderId);

                    String logSql = "INSERT INTO `jiayuw_product_log` set `order_id`=?,`product_id`=?, `old_stock`=?, `new_stock`=?, `stock`=?;";
                    PreparedStatement LogStmt = connection.prepareStatement(logSql);
                    LogStmt.setInt(1, orderId);
                    LogStmt.setInt(2, id);
                    LogStmt.setInt(3, oldStock);
                    LogStmt.setInt(4, newStock);
                    LogStmt.setInt(5, -1);
                    LogStmt.executeUpdate();
                }
               PreparedStatement stmt = connection.prepareStatement(reduceSql);
               stmt.setInt(1, id);
               stmt.executeUpdate();
            }
            return true;
        }catch (Exception exception){
            exception.printStackTrace();
            return false;
        }

    }

    private Integer getLastOrderId(){
        try{
            String query = "SELECT * FROM jiayuw_orders WHERE 1 order by id desc limit 1;";
            PreparedStatement stmtP = connection.prepareStatement(query);
            ResultSet rs = stmtP.executeQuery();
            if(rs.next()) {
                Integer id= rs.getInt("id");
                return id;
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return 0;
    }

    private String getDatetime(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return currentDateTime.format(formatter);
    }
}
