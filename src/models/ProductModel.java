package models;

import dao.DBConnect;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
}
