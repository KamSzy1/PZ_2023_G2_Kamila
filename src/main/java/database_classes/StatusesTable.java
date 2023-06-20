package database_classes;

/**
 * Klasa reprezentująca tabelę "Statuses" z bazy danych
 */
public class StatusesTable {

    /**
     * ID
     */
    private int idStatus;
    /**
     * Nazwa
     */
    private String name;

    /**
     * Metoda zwracająca numer ID statusu
     *
     * @return ID statusu
     */
    public int getIdStatus() {
        return idStatus;
    }

    /**
     * Metoda zwracająca nazwę statusu
     *
     * @return nazwa statusu
     */
    public String getName() {
        return name;
    }

    /**
     * Metoda ustawiająca numer ID statusu
     *
     * @param idStatus ustawiony ID statusu
     */
    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }

    /**
     * Metoda ustawiająca nazwę ID statusu
     *
     * @param name ustawiona nazwa statusu
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Metoda toString
     *
     * @return toString
     */
    @Override
    public String toString() {
        return name;
    }
}
