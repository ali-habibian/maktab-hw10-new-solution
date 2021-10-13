package q1.manager;

import q1.dao.UserDao;
import q1.db.DbConnection;
import q1.exceptions.UserNotFoundException;
import q1.model.Item;
import q1.model.Patient;
import q1.model.Prescription;
import q1.model.User;
import q1.utilities.Input;

import java.util.ArrayList;

public class UiManager {
    public void showMenu() {
        boolean isExit = false;
        while (!isExit) {
            System.out.println("*** PHARMACY MANAGEMENT SYSTEM ***");
            System.out.println("--- Select your role:  ---");
            System.out.println("1- Patient");
            System.out.println("2- Admin");
            System.out.println("3- exit");
            int option = Input.getIntInputValue("");

            switch (option) {
                case 1:

                        showPatientMenu();
                    break;
                case 2:
                    break;
                case 3:
                    isExit = true;
                    break;
                default:
            }
        }

    }

    private void showPatientMenu(){
        String name = Input.getStringInputValue("Enter your name: ");
        String password = Input.getStringInputValue("Enter your password: ");
        UserDao<Patient> patientDao = new UserDao<>(DbConnection.getConnection(), new Patient(name, password));
        try {
            User patient = patientDao.login();
                boolean isLogout = false;
                while (!isLogout) {
                    System.out.println("*** PATIENT MENU ***");
                    System.out.println("--- Select your option:  ---");
                    System.out.println("1- Add prescription");
                    System.out.println("2- See confirmed prescriptions");
                    System.out.println("3- Edit prescription");
                    System.out.println("4- Delete prescription");
                    System.out.println("5- Logout");

                    int option = Input.getIntInputValue("");

                    switch (option) {
                        case 1:
                            showPatientMenu();
                            break;
                        case 2:
                            seeConfirmedPrescriptions((Patient) patient);
                            break;
                        case 3:
                            break;
                        case 4:
                            break;
                        case 5:
                            isLogout = true;
                            break;
                        default:
                    }
                }
        } catch (UserNotFoundException e) {
            System.err.println(e.getMessage());
            showMenu();
        }
    }

    private void seeConfirmedPrescriptions(Patient patient) {
        ArrayList<Prescription> prescriptions = patient.getPrescriptions();
        for (var prescription : prescriptions) {
            if (prescription.getIsConfirmed() == 1) {
                int prescriptionId = prescription.getId();
                System.out.println("--- Prescription id: " + prescriptionId);
                ArrayList<Item> items = prescription.getItems();
                double prescriptionItemsTotalPrice = 0;
                for (var item : items) {
                    String itemName = item.getName();
                    int itemDoseExist = item.getDoseExist();
                    double itemPrice = item.getPrice();
                    prescriptionItemsTotalPrice += itemPrice;
                    System.out.println("name: " + itemName + ", price: " + itemPrice);
                }
                System.out.println("prescription " + prescriptionId + " Items Total Price: " + prescriptionItemsTotalPrice);
            }
        }
    }
}
