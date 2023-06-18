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

    public int getIdStatus() {
        return idStatus;
    }

    public String getName() {
        return name;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
