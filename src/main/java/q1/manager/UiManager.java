package q1.manager;

import q1.dao.UserDao;
import q1.db.DbConnection;
import q1.exceptions.UserNotFoundException;
import q1.model.*;
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
                    showAdminMenu();
                    break;
                case 3:
                    isExit = true;
                    break;
                default:
            }
        }
    }

    private void showAdminMenu() {
        String name = Input.getStringInputValue("Enter your name: ");
        String password = Input.getStringInputValue("Enter your password: ");
        UserDao<Admin> adminDao = new UserDao<>(DbConnection.getConnection(), new Admin(name, password));
        try {
            User admin = adminDao.login();
            adminLoginMenu(adminDao, (Admin) admin);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void adminLoginMenu(UserDao<Admin> adminDao, Admin admin) {
        boolean isLogout = false;
        while (!isLogout) {
            System.out.print(Color.RED_BOLD_BRIGHT);
            System.out.println("*** ADMIN MENU ***");
            System.out.print(Color.RESET);
            System.out.println("--- Select your option:  ---");
            System.out.println("1- See all prescriptions");
            System.out.println("2- Confirm prescriptions");
            System.out.println("3- Logout");

            int option = Input.getIntInputValue("");

            switch (option) {
                case 1:
                    seeAllPrescriptions(adminDao, (Admin) admin);
                    break;
                case 2:
                    try {
                        ConfirmPrescriptions(adminDao, (Admin) admin);
                    } catch (UserNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    isLogout = true;
                    admin = null;
                    break;
                default:
            }
        }
    }

    private void ConfirmPrescriptions(UserDao<Admin> adminDao, Admin admin) throws UserNotFoundException {
        adminDao.seeNotConfirmedPrescriptionsId(admin);
        int prescriptionId = Input.getIntInputValue("Enter prescription id you want to confirm: ");
        boolean done = false;
        while (!done) {
            adminDao.seeItemsByPrescriptionId(prescriptionId);
            adminDao.updatePrescriptionById(prescriptionId);
            int itemId = Input.getIntInputValue("Enter item id to edit it: ");
            double price = Input.getDoubleInputValue("Enter price: ");
            adminDao.updateItemPriceById(itemId, price);
            Printer.printInfoMessage("item with id " + itemId + " is updated.");
            System.out.println("Continue or Back?");
            System.out.println("1.Continue");
            System.out.println("2.Back");
            int option = Input.getIntInputValue("");
            if (option == 2) {
                adminDao.login();
                done = true;
            }
        }
    }

    private void seeAllPrescriptions(UserDao<Admin> adminDao, Admin admin) {
        adminDao.seeAllPrescriptionsId(admin);
    }

    private void showPatientMenu() {
        String name = Input.getStringInputValue("Enter your name: ");
        String password = Input.getStringInputValue("Enter your password: ");
        UserDao<Patient> patientDao = new UserDao<>(DbConnection.getConnection(), new Patient(name, password));
        try {
            User patient = patientDao.login();
            patientLoginMenu(patientDao, (Patient) patient);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void patientLoginMenu(UserDao<Patient> patientDao, Patient patient) {
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
                    try {
                        editPrescription(patientDao, (Patient) patient);
                    } catch (UserNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    deletePrescription(patientDao, (Patient) patient);
                    break;
                case 5:
                    isLogout = true;
                    patient = null;
                    break;
                default:
            }
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

    private void editPrescription(UserDao<Patient> patientDao, Patient patient) throws UserNotFoundException {
        patientDao.seeNotConfirmedPrescriptionsId(patient);
        System.out.println("Enter id of prescription you want to edit.");
        int prescriptionId = Input.getIntInputValue("");
        while (true) {
            System.out.println("1.Add new item");
            System.out.println("2.Delete item");
            System.out.println("3.Back");
            int option = Input.getIntInputValue("");
            if (option == 1) {
                patientDao.seeNotConfirmedPrescriptionItems(prescriptionId, patient);
                System.out.println("Enter item name: ");
                String itemName = Input.getStringInputValue("");
                patientDao.addNewItemToPrescription(itemName, prescriptionId);
                patientDao.login();
            }
            if (option == 2) {
                patientDao.seeNotConfirmedPrescriptionItems(prescriptionId, patient);
                System.out.println("Enter item id you want to delete: ");
                int itemId = Input.getIntInputValue("");
                patientDao.deleteItemById(itemId);
                patientDao.login();
            }
            if (option == 3) {
                patientLoginMenu(patientDao, patient);
            }
        }
    }

    private void deletePrescription(UserDao<Patient> patientDao, Patient patient) {
        patientDao.seeNotConfirmedPrescriptionsId(patient);
        System.out.println("Enter id of prescription you want to delete.");
        int prescriptionId = Input.getIntInputValue("");
        patientDao.deletePrescriptionById(prescriptionId);
        Printer.printInfoMessage("Prescription with id " + prescriptionId + " is deleted");
        patientLoginMenu(patientDao, patient);
    }
}
