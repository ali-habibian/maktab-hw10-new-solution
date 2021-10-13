package q1;

import q1.dao.PrescriptionDao;
import q1.dao.UserDao;
import q1.db.DbConnection;
import q1.exceptions.NoDataException;
import q1.exceptions.UserNotFoundException;
import q1.manager.UiManager;
import q1.model.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        UiManager uiManager = new UiManager();
        uiManager.showMenu();

    }
}
