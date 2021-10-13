package q1.dao;

import q1.exceptions.NoDataException;
import q1.exceptions.NotValidSizeItemsInPrescriptionException;
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

    public ArrayList<Prescription> getPrescriptionByPatientId(int userId){
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
}
