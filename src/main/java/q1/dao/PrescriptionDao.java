package q1.dao;

import q1.exceptions.NoDataException;
import q1.exceptions.NotValidSizeItemsInPrescriptionException;
import q1.model.Item;
import q1.model.Patient;
import q1.model.Prescription;
import q1.model.User;
import q1.utilities.Constants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PrescriptionDao {
    private Connection connection;

    public PrescriptionDao(Connection connection) {
        this.connection = connection;
    }

    public Prescription addPrescription(Prescription prescription) {
        Prescription resultPrescription = new Prescription();
        ItemDao itemDao = new ItemDao(connection);

        try (
                PreparedStatement ps = connection.prepareStatement(Constants.ADD_PRESCRIPTION_QUERY)
        ) {
            ps.setInt(1, prescription.getPatientId());
            int result = ps.executeUpdate();
            if (result == 1) {
                resultPrescription = getLastAddedPrescription();
                ArrayList<Item> addedToDbItems = new ArrayList<>();
                for (Item item : prescription.getItems()) {
                    addedToDbItems.add(itemDao.addItem(item));
                }
                resultPrescription.setItems(addedToDbItems);
                for (Item item : resultPrescription.getItems()) {
                    addToPrescriptionItem(resultPrescription.getId(), item.getId());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultPrescription;
    }

    private Prescription getLastAddedPrescription() {
        Prescription prescription = new Prescription();

        try (
                PreparedStatement ps = connection.prepareStatement(Constants.GET_LAST_ADDED_PRESCRIPTION_QUERY)
        ) {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                prescription.setId(resultSet.getInt("id"));
                prescription.setPatientId(resultSet.getInt("patient_id"));
                prescription.setIsConfirmed(resultSet.getInt("is_confirmed"));
                prescription.setAdminId(resultSet.getInt("admin_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return prescription;
    }

    public void addToPrescriptionItem(int prescriptionId, int itemId) {
        try (
                PreparedStatement ps = connection.prepareStatement(Constants.ADD_TO_PRESCRIPTION_ITEM_TABLE_QUERY)
        ) {
            ps.setInt(1, prescriptionId);
            ps.setInt(2, itemId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Prescription> getPrescriptionByPatientId(int userId) {
        ArrayList<Prescription> prescriptions = new ArrayList<>();
        try (
                PreparedStatement ps = connection.prepareStatement(Constants.GET_PRESCRIPTION_BY_PATIENT_ID_QUERY)
        ) {
            ps.setInt(1, userId);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                ItemDao itemDao = new ItemDao(connection);
                Prescription prescription = new Prescription();
                prescription.setId(resultSet.getInt("id"));
                prescription.setPatientId(resultSet.getInt("patient_id"));
                prescription.setIsConfirmed(resultSet.getInt("is_confirmed"));
                prescription.setAdminId(resultSet.getInt("admin_id"));
                prescription.setItems(itemDao.getItemByPrescriptionId(resultSet.getInt("id")));
                prescriptions.add(prescription);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prescriptions;
    }

    public void deletePrescriptionById(int prescriptionId) {
        try (
                PreparedStatement ps = connection.prepareStatement(Constants.DELETE_PRESCRIPTION_BY_ID)
        ) {
            ps.setInt(1, prescriptionId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
