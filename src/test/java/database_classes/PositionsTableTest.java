package database_classes;
import org.junit.jupiter.api.Test;;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PositionsTableTest {

    PositionsTable positionsTable = new PositionsTable();

    @Test
    void testGetIdPosition() {
        int expectedIdPosition = 2;
        positionsTable.setIdPosition(expectedIdPosition);
        int actualIdPosition = positionsTable.getIdPosition();
        assertEquals(expectedIdPosition, actualIdPosition);
    }

    @Test
    void testGetPositionName() {
        String expectedPositionName = "Kierownik";
        positionsTable.setPositionName(expectedPositionName);
        String actualPositionName = positionsTable.getPositionName();
        assertEquals(expectedPositionName, actualPositionName);
    }

    @Test
    void testGetDescription() {
        String expectedDescription = "Przykladowy opis";
        positionsTable.setDescription(expectedDescription);
        String actualPositionName = positionsTable.getDescription();
        assertEquals(expectedDescription, actualPositionName);
    }

    @Test
    void testSetIdPosition() {
        int idPosition = 1;
        positionsTable.setIdPosition(idPosition);
        int actualIdPosition = positionsTable.getIdPosition();
        assertEquals(idPosition, actualIdPosition);
    }

    @Test
    void testSetDescription() {
        String description = "Przykladowy opis";
        positionsTable.setDescription(description);
        String actualDescription = positionsTable.getDescription();
        assertEquals(description, actualDescription);
    }

    @Test
    void testSetPositionName() {
        String expectedPositionName = "Kierownik";
        positionsTable.setPositionName(expectedPositionName);
        String actualPositionName = positionsTable.getPositionName();
        assertEquals(expectedPositionName, actualPositionName);
    }

    @Test
    void toStringTest(){
        PositionsTable postionsTable = new PositionsTable();
        postionsTable.setPositionName("Zrealizowano");
        assertEquals(postionsTable.toString(), "Zrealizowano");
    }
}

