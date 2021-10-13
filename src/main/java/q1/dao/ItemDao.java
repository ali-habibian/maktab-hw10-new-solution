package q1.dao;

import q1.exceptions.NoDataException;
import q1.model.Item;
import q1.utilities.Constants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDao {
    private Connection connection;

    public ItemDao(Connection connection) {
        this.connection = connection;
    }

    public ArrayList<Item> getItemByPrescriptionId(int prescriptionId) {
        ArrayList<Item> items = new ArrayList<>();
        try (
                PreparedStatement ps = connection.prepareStatement(Constants.GET_ITEM_BY_PRESCRIPTION_ID_QUERY)
        ) {
            ps.setInt(1, prescriptionId);
            ResultSet resultSet = ps.executeQuery();

                while (resultSet.next()){
                    Item item = new Item();
                    item.setId(resultSet.getInt("id"));
                    item.setName(resultSet.getString("name"));
                    item.setDoseExist(resultSet.getInt("does_exist"));
                    item.setPrice(resultSet.getDouble("price"));

                    items.add(item);
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
}