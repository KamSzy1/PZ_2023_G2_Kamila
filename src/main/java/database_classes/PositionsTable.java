package database_classes;

/**
 * Klasa reprezentująca tabelę "Positions" z bazy danych
 */
public class PositionsTable {

    /**
     * ID
     */
    private int idPosition;
    /**
     * Nazwa stanowiska
     */
    private String positionName;
    /**
     * Opis stanowiska
     */
    private String description;

    /**
     * Metoda zwracająca numer stanowiska pracownika
     *
     * @return ID stanowiska
     */
    public int getIdPosition() {
        return idPosition;
    }

    /**
     * Metoda zwracająca nazwę stanowiska
     *
     * @return nazwa stanowiska
     */
    public String getPositionName() {
        return positionName;
    }

    /**
     * Metoda zwracająca opis stanowiska
     *
     * @return opis stanowiska
     */
    public String getDescription() {
        return description;
    }

    /**
     * Metoda ustawiająca numer stanowiska pracownika
     *
     * @param idPosition wybrane ID stanowiska
     */
    public void setIdPosition(int idPosition) {
        this.idPosition = idPosition;
    }

    /**
     * Metoda ustawiająca nazwę stanowiska pracownika
     *
     * @param positionName ustalona nazwa stanowiska
     */
    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    /**
     * Metoda ustawiająca opis stanowiska pracownika
     *
     * @param description opis stanowiska
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Metoda zwracająca obiekt jako ciąg znaków
     *
     * @return ciąg znaków
     */
    @Override
    public String toString() {
        return positionName;
    }
}
