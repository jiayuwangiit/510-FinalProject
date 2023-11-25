package models;

import dao.DBConnect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
