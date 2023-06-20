package database_classes;

import javafx.scene.control.Button;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UsersTableTest {

    @Test
    public void UsersTableTest() {

        int idUser = 1;
        String name = "Uga";
        String surname = "Buga";
        String address = "Świątynna 30";
        String zip = "12-321";
        String place = "Miasteczko";
        int phoneNumber = 123456789;
        int positionId = 2;
        String token = "tokenix";
        int groups = 3;
        Button editEmployeeButton;
        UsersTable usersTable = new UsersTable();

        usersTable.setIdUser(idUser);
        usersTable.setName(name);
        usersTable.setSurname(surname);
        usersTable.setAddress(address);
        usersTable.setZip(zip);
        usersTable.setPlace(place);
        usersTable.setPhoneNumber(phoneNumber);
        usersTable.setPositionId(positionId);
        usersTable.setToken(token);
        usersTable.setGroups(groups);

        int actualIdUser = usersTable.getIdUser();
        String actualName = usersTable.getName();
        String actualSurname = usersTable.getSurname();
        String actualAddress = usersTable.getAddress();
        String actualZip = usersTable.getZip();
        String actualPlace = usersTable.getPlace();
        int actualPhoneNumber = usersTable.getPhoneNumber();
        int actualPositionId = usersTable.getPositionId();
        String actualToken = usersTable.getToken();
        int actualGroups = usersTable.getGroups();

        editEmployeeButton= usersTable.getEditEmployeeButton();
        assertNull(editEmployeeButton);

        assertEquals(idUser, actualIdUser);
        assertEquals(name, actualName);
        assertEquals(surname, actualSurname);
        assertEquals(address, actualAddress);
        assertEquals(zip, actualZip);
        assertEquals(place, actualPlace);
        assertEquals(phoneNumber, actualPhoneNumber);
        assertEquals(positionId, actualPositionId);
        assertEquals(token, actualToken);
        assertEquals(groups, actualGroups);
    }
}
