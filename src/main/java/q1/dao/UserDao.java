package q1.dao;

import q1.exceptions.NoDataException;
import q1.exceptions.UserNotFoundException;
import q1.model.Admin;
import q1.model.Patient;
import q1.model.Prescription;
import q1.model.User;
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

        try (
                PreparedStatement ps = connection.prepareStatement(Constants.GET_PATIENT_BY_NAME_PASSWORD)
        ) {
            ps.setString(1, name);
            ps.setString(2, password);

            ResultSet resultSet = ps.executeQuery();
            if (!resultSet.next()){
                throw new UserNotFoundException("Name or Password is not valid.");
            }
            PrescriptionDao prescriptionDao = new PrescriptionDao(connection);
            while (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                if (checkType() == 1) {
                    user.setPrescriptions(prescriptionDao.getPrescriptionByPatientId(user.getId()));
                } else if (checkType() == 2) {
                    // TODO add prescription to admin
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void logout(User user) {
        user = null;
    }

}
