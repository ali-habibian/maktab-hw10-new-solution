package q1.model;

import q1.exceptions.NotValidSizeItemsInPrescriptionException;
import q1.utilities.Constants;

import java.util.ArrayList;

public class Prescription {
    private int id;
    private int patientId;
    private int adminId;
    private int isConfirmed;
    private ArrayList<Item> items;

    public Prescription() {
    }

    public Prescription(int id, int patientId, int adminId, int isConfirmed, ArrayList<Item> items) {
        this.id = id;
        this.patientId = patientId;
        this.adminId = adminId;
        this.isConfirmed = isConfirmed;
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getIsConfirmed() {
        return isConfirmed;
    }

    public void setIsConfirmed(int isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
//        if (items.size() >= 1) {
//            if (items.size() <= Constants.MAX_ITEMS_IN_EACH_PRESCRIPTION) {
//                this.items = items;
//            }
//        }
//
//        throw new NotValidSizeItemsInPrescriptionException("Items count is not valid");

        this.items = items;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }
}
