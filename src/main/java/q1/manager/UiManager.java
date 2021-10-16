package q1.manager;

import q1.dao.UserDao;
import q1.db.DbConnection;
import q1.exceptions.UserNotFoundException;
import q1.model.Item;
import q1.model.Patient;
import q1.model.Prescription;
import q1.model.User;
import q1.utilities.Color;
import q1.utilities.Input;
import q1.utilities.Printer;

import java.util.ArrayList;

public class UiManager {
    public void showMenu() {
        boolean isExit = false;
        while (!isExit) {
            System.out.print(Color.RED_BOLD_BRIGHT);
            System.out.println("*** PHARMACY MANAGEMENT SYSTEM ***");
            System.out.print(Color.RESET);
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

    private void showPatientMenu() {
        String name = Input.getStringInputValue("Enter your name: ");
        String password = Input.getStringInputValue("Enter your password: ");
        UserDao<Patient> patientDao = new UserDao<>(DbConnection.getConnection(), new Patient(name, password));
        try {
            User patient = patientDao.login();
            boolean isLogout = false;
            while (!isLogout) {
                System.out.print(Color.RED_BOLD_BRIGHT);
                System.out.println("*** PATIENT MENU ***");
                System.out.print(Color.RESET);
                System.out.println("--- Select your option:  ---");
                System.out.println("1- Add prescription");
                System.out.println("2- See confirmed prescriptions");
                System.out.println("3- Edit prescription");
                System.out.println("4- Delete prescription");
                System.out.println("5- Logout");

                int option = Input.getIntInputValue("");

                switch (option) {
                    case 1:
                        addPrescription(patientDao, (Patient) patient);
                        break;
                    case 2:
                        seeConfirmedPrescriptions(patientDao, (Patient) patient);
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

    private void addPrescription(UserDao<Patient> patientDao, Patient patient) {
        Prescription prescription = new Prescription();
        prescription.setPatientId(patient.getId());
        ArrayList<Item> items = new ArrayList<>();
        boolean isDone = false;
        int itemCounter = 0;
        while (!isDone) {
            String itemName = Input.getStringInputValue("Enter item name: ");
            Item item = new Item();
            item.setName(itemName);
            items.add(item);
            Printer.printInfoMessage("Want to add more?");
            System.out.println("1.yes");
            System.out.println("2.No");
            int option = Input.getIntInputValue("");
            if (itemCounter >= 9) {
                Printer.printInfoMessage("You can not add more than 10 item.");
                isDone = true;
            }
            if (option == 1) {
                itemCounter++;
            } else {
                isDone = true;
            }
            prescription.setItems(items);
        }
        patient.getPrescriptions().add(patientDao.addPrescription(prescription));
    }

    private void seeConfirmedPrescriptions(UserDao<Patient> patientDao, Patient patient) {
        patientDao.seeConfirmedPrescriptions(patient);
    }
}
