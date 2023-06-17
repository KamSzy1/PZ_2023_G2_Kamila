package database_classes;

/**
 * Klasa reprezentująca tabelę "Positions" z bazy danych
 */
public class PositionsTable {

    /**
     * @param idPosition ID
     */
    private int idPosition;
    /**
     * @param positionName Nazwa stanowiska
     */
    private String positionName;
    /**
     * @param description Opis stanowiska
     */
    private String description;

    public int getIdPosition() {
        return idPosition;
    }

    public String getPositionName() {
        return positionName;
    }

    public String getDescription() {
        return description;
    }

    public void setIdPosition(int idPosition) {
        this.idPosition = idPosition;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
