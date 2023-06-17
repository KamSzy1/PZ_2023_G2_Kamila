package database_classes;

/**
 * Klasa reprezentująca tabelę "Statuses" z bazy danych
 */
public class StatusesTable {

    /**
     * @param idStatus ID
     */
    private int idStatus;
    /**
     * @param name Nazwa
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

}
