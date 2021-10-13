package q1.utilities;

public class Constants {
    public static final String DB_URL = "jdbc:mysql://localhost:3306/pharmacy";
    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "";

    public static final int MIN_ITEMS_IN_EACH_PRESCRIPTION = 3;
    public static final int MAX_ITEMS_IN_EACH_PRESCRIPTION = 10;

    public static final String GET_PATIENT_BY_NAME_PASSWORD =
            "SELECT\n" +
                    "\tpatients.* \n" +
                    "FROM\n" +
                    "\tpatients \n" +
                    "WHERE\n" +
                    "\tpatients.`name` = ? \n" +
                    "\tAND patients.`password` = ?";

    public static final String GET_PRESCRIPTION_BY_PATIENT_ID_QUERY =
            "SELECT\n" +
                    "\tprescriptions.id,\n" +
                    "\tprescriptions.patient_id,\n" +
                    "\tprescriptions.is_confirmed,\n" +
                    "\tprescriptions.admin_id \n" +
                    "FROM\n" +
                    "\tprescriptions \n" +
                    "WHERE\n" +
                    "\tprescriptions.patient_id = ?";

    public static final String GET_ITEM_BY_PRESCRIPTION_ID_QUERY =
            "SELECT\n" +
                    "\titems.id,\n" +
                    "\titems.`name`,\n" +
                    "\titems.does_exist,\n" +
                    "\titems.price \n" +
                    "FROM\n" +
                    "\titems\n" +
                    "\tINNER JOIN prescription_item ON items.id = prescription_item.item_id \n" +
                    "WHERE\n" +
                    "\tprescription_item.prescription_id = ?";

    public static final String ADD_PRESCRIPTION_QUERY =
            "INSERT INTO `pharmacy`.`prescriptions` (`patient_id`) VALUES (?)";

    public static final String ADD_ITEM_QUERY =
            "INSERT INTO `pharmacy`.`items` (`name`) VALUES (?)";

    public static final String UPDATE_ITEM_QUERY =
            "UPDATE `pharmacy`.`items` SET `name` = ?, `does_exist` = ?, `price` = ? WHERE `id` = ?";

    public static final String DELETE_ITEM_QUERY =
            "DELETE FROM `pharmacy`.`items` WHERE `id` = ?";

    public static final String ADD_PATIENT_ID_PRESCRIPTIONS_QUERY =
            "INSERT INTO `pharmacy`.`prescriptions` (`patient_id`) VALUES (?)";

    public static final String GET_CONFIRMED_PRESCRIPTIONS_QUERY =
            "SELECT" +
                    "prescription_item.prescription_id," +
                    "prescriptions.is_confirmed," +
                    "items.*," +
                    "prescriptions.patient_id " +
                    "FROM" +
                    "prescription_item" +
                    "INNER JOIN prescriptions ON prescription_item.prescription_id = prescriptions.id" +
                    "INNER JOIN patients ON patients.id = prescriptions.patient_id" +
                    "INNER JOIN items ON prescription_item.item_id = items.id " +
                    "WHERE" +
                    "prescriptions.patient_id = ? " +
                    "AND prescription_item.prescription_id = ? " +
                    "AND prescriptions.is_confirmed = ?";

    public static final String GET_CONFIRMED_PRESCRIPTIONS_ID_BY_PATIENT_ID_QUERY =

            "SELECT\n" +
                    "\tprescriptions.* \n" +
                    "FROM\n" +
                    "\tprescriptions \n" +
                    "WHERE\n" +
                    "\tprescriptions.patient_id = ? \n" +
                    "\tAND prescriptions.is_confirmed = 1";

    public static final String GET_ITEMS_BY_PRESCRIPTIONS_ID_QUERY =
            "SELECT\n" +
                    "\titems.* \n" +
                    "FROM\n" +
                    "\titems\n" +
                    "\tINNER JOIN prescription_item ON items.id = prescription_item.item_id\n" +
                    "\tINNER JOIN prescriptions ON prescription_item.prescription_id = prescriptions.id \n" +
                    "WHERE\n" +
                    "\tprescription_item.prescription_id = ?";

    public static final String GET_ALL_PRESCRIPTIONS_ID_QUERY =
            "SELECT\n" +
                    "\tprescriptions.id,\n" +
                    "\tprescriptions.is_confirmed \n" +
                    "FROM\n" +
                    "\tprescriptions";
}
