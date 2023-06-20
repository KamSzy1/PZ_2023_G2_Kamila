package database_classes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class StatusesTableTest {

    StatusesTable statusesTable = new StatusesTable();

    @Test
    void testGetIdStatus() {
        int expectedIdStatus = 1;
        statusesTable.setIdStatus(expectedIdStatus);
        int actualIdStatus = statusesTable.getIdStatus();
        assertEquals(expectedIdStatus, actualIdStatus);
    }

    @Test
    void testGetName() {
        String expectedName = "Po terminie";
        statusesTable.setName(expectedName);
        String actualName = statusesTable.getName();
        assertEquals(expectedName, actualName);
    }

    @Test
    void testSetIdStatus() {
        int idStatus = 1;
        statusesTable.setIdStatus(idStatus);
        int actualIdStatus = statusesTable.getIdStatus();
        assertEquals(idStatus, actualIdStatus);
    }

    @Test
    void testSetName() {
        String name = "Po terminie";
        statusesTable.setName(name);
        String actualName = statusesTable.getName();
        assertEquals(name, actualName);
    }

    @Test
    void toStringTest(){
        StatusesTable statusesTable = new StatusesTable();
        statusesTable.setName("Zrealizowano");
        assertEquals(statusesTable.toString(), "Zrealizowano");
    }
}
