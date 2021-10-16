package q1.dao;

import q1.exceptions.UserNotFoundException;
import q1.model.*;
import q1.utilities.Color;
import q1.utilities.Constants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao<T extends User> {
    private Connection connection;
    private T user;

    public UserDao(Connection connection, T user) {
        this.connection = connection;
        this.user = user;
    }

    private int checkType() {
        if (user instanceof Patient)
            return 1;
        else
            return 2;
    }

    public User login() throws UserNotFoundException {
        String name = user.getName();
        String password = user.getPassword();

        if (checkType() == 1) {
            try (
                    PreparedStatement ps = connection.prepareStatement(Constants.GET_PATIENT_BY_NAME_PASSWORD)
            ) {
                ps.setString(1, name);
                ps.setString(2, password);

                ResultSet resultSet = ps.executeQuery();
//                if (!resultSet.next()) {
//                    throw new UserNotFoundException("Name or Password is not valid.");
//                }
                while (resultSet.next()) {
                    user.setId(resultSet.getInt("id"));
                    user.setName(resultSet.getString("name"));
                    user.setPassword(resultSet.getString("password"));
                    PrescriptionDao prescriptionDao = new PrescriptionDao(connection);
                    user.setPrescriptions(prescriptionDao.getPrescriptionByUserId(
                            user.getId(), Constants.GET_PRESCRIPTION_BY_PATIENT_ID_QUERY));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try (
                    PreparedStatement ps = connection.prepareStatement(Constants.GET_ADMIN_BY_NAME_PASSWORD)
            ) {
                ps.setString(1, name);
                ps.setString(2, password);

                ResultSet resultSet = ps.executeQuery();

                while (resultSet.next()) {
                    user.setId(resultSet.getInt("id"));
                    user.setName(resultSet.getString("name"));
                    user.setPassword(resultSet.getString("password"));
                    PrescriptionDao prescriptionDao = new PrescriptionDao(connection);
                    user.setPrescriptions(prescriptionDao.getPrescriptionByUserId(
                            user.getId(), Constants.GET_PRESCRIPTION_BY_ADMIN_ID_QUERY));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    public void seeConfirmedPrescriptions(T user) {
        ArrayList<Prescription> prescriptions = user.getPrescriptions();
        for (var prescription : prescriptions) {
            if (prescription.getIsConfirmed() == 1) {
                int prescriptionId = prescription.getId();
                System.out.print(Color.CYAN_BOLD);
                System.out.println("--- Prescription id: " + prescriptionId);
                System.out.print(Color.RESET);

                ArrayList<Item> items = prescription.getItems();
                double prescriptionItemsTotalPrice = 0;
                for (var item : items) {
                    String itemName = item.getName();
                    double itemPrice = item.getPrice();
                    prescriptionItemsTotalPrice += itemPrice;
                    System.out.println("name: " + itemName + ", price: " + itemPrice);
                }
                System.out.print(Color.MAGENTA_BOLD);
                System.out.println("prescription " + prescriptionId + " Items Total Price: " + prescriptionItemsTotalPrice);
                System.out.print(Color.RESET);
            }
        }
    }

    public void seeNotConfirmedPrescriptionsId(T user) {
        ArrayList<Prescription> prescriptions = user.getPrescriptions();
        for (var prescription : prescriptions) {
            if (prescription.getIsConfirmed() == 0) {
                int prescriptionId = prescription.getId();
                System.out.print(Color.CYAN_BOLD);
                System.out.println("--- Prescription id: " + prescriptionId);
                System.out.print(Color.RESET);
            }
        }
    }

    public void seeAllPrescriptionsId(T user) {
        ArrayList<Prescription> prescriptions = user.getPrescriptions();
        for (var prescription : prescriptions) {
            int prescriptionId = prescription.getId();
            System.out.print(Color.CYAN_BOLD);
            System.out.println("--- Prescription id: " + prescriptionId + ", is confirmed: " + prescription.getIsConfirmed());
            System.out.print(Color.RESET);

        }
    }

    public void seeNotConfirmedPrescriptionItems(int prescriptionId, T user) {
        ArrayList<Prescription> prescriptions = user.getPrescriptions();
        for (var prescription : prescriptions) {
            if (prescription.getId() == prescriptionId) {
                System.out.print(Color.CYAN_BOLD);
                System.out.println("--- Prescription id: " + prescriptionId);
                System.out.print(Color.RESET);

                ArrayList<Item> items = prescription.getItems();
                for (var item : items) {
                    int itemId = item.getId();
                    String itemName = item.getName();
                    System.out.println("id: " + itemId + ", name: " + itemName);
                }
            }
        }
    }

    public void seeItemsByPrescriptionId(int prescriptionId) {
        ItemDao itemDao = new ItemDao(connection);
        ArrayList<Item> items = itemDao.getItemByPrescriptionId(prescriptionId);
        for (Item item : items) {
            int itemId = item.getId();
            String itemName = item.getName();
            int doseExist = item.getDoseExist();
            double itemPrice = item.getPrice();
            System.out.println("id: " + itemId + ", name: " + itemName + ", dose exist: " + doseExist + ", price: " + itemPrice);
        }
    }

    public void addNewItemToPrescription(String itemName, int prescriptionId) {
        Item item = new Item();
        item.setName(itemName);
        ItemDao itemDao = new ItemDao(connection);
        Item item1 = itemDao.addItem(item);
        PrescriptionDao prescriptionDao = new PrescriptionDao(connection);
        prescriptionDao.addToPrescriptionItem(prescriptionId, item1.getId());
    }

    public void deleteItemById(int id) {
        ItemDao itemDao = new ItemDao(connection);
        itemDao.deleteItemById(id);
    }

    public Prescription addPrescription(Prescription prescription) {
        PrescriptionDao prescriptionDao = new PrescriptionDao(connection);
        return prescriptionDao.addPrescription(prescription);
    }

    public void deletePrescriptionById(int prescriptionId) {
        PrescriptionDao prescriptionDao = new PrescriptionDao(connection);
        prescriptionDao.deletePrescriptionById(prescriptionId);
    }

    public void updatePrescriptionById(int prescriptionId) {
        PrescriptionDao prescriptionDao = new PrescriptionDao(connection);
        prescriptionDao.updatePrescriptionById(prescriptionId);
    }

    public void updateItemPriceById(int itemId, double price) {
        ItemDao itemDao = new ItemDao(connection);
        itemDao.updateItemPriceById(itemId, price);
    }
}
