package q1.utilities;

public class Constants {
    public static final String DB_URL = "jdbc:mysql://localhost:3306/pharmacy";
    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "";

    public static final int MIN_ITEMS_IN_EACH_PRESCRIPTION = 3;
    public static final int MAX_ITEMS_IN_EACH_PRESCRIPTION = 10;

    //language=MySQL
    public static final String GET_PATIENT_BY_NAME_PASSWORD =
            "SELECT patients.id, patients.`name`, patients.`password` " +
                    "FROM patients WHERE patients.`name` = ? AND patients.`password` = ?";

    //language=MySQL
    public static final String GET_PRESCRIPTION_BY_PATIENT_ID_QUERY =
            "SELECT prescriptions.* FROM prescriptions WHERE prescriptions.patient_id = ?";

    //language=MySQL
    public static final String GET_ITEM_BY_PRESCRIPTION_ID_QUERY =
            "SELECT items.* FROM items INNER JOIN prescription_item " +
                    "ON items.id = prescription_item.item_id WHERE prescription_item.prescription_id = ?";

    //language=MySQL
    public static final String GET_ITEM_BY_NAME_QUERY =
            "SELECT items.* FROM items WHERE items.`name` = ?";

    //language=MySQL
    public static final String ADD_ITEM_QUERY =
            "INSERT INTO `pharmacy`.`items` (`name`) VALUES (?)";

    //language=MySQL
    public static final String UPDATE_ITEM_QUERY =
            "UPDATE `pharmacy`.`items` SET `name` = ?, `does_exist` = ?, `price` = ? WHERE `id` = ?";

    //language=MySQL
    public static final String DELETE_ITEM_QUERY =
            "DELETE FROM `pharmacy`.`items` WHERE `id` = ?";

    //language=MySQL
    public static final String GET_LAST_ADDED_PRESCRIPTION_QUERY =
            "SELECT prescriptions.* FROM prescriptions WHERE prescriptions.id = (SELECT max( id ) FROM pharmacy.prescriptions)";

    //language=MySQL
    public static final String ADD_PRESCRIPTION_QUERY =
            "INSERT INTO `pharmacy`.`prescriptions` (`patient_id`) VALUES (?)";

    //language=MySQL
    public static final String ADD_TO_PRESCRIPTION_ITEM_TABLE_QUERY =
            "INSERT INTO `pharmacy`.`prescription_item` (`prescription_id`, `item_id`) VALUES (?, ?)";

    //language=MySQL
    public static final String GET_CONFIRMED_PRESCRIPTIONS_QUERY =
            "";

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
