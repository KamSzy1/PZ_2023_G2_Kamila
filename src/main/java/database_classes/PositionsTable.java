package database_classes;

public class PositionsTable {
    private int id_position;
    private String position_name;
    private String description;

    public int getId_position() {
        return id_position;
    }

    public void setId_position(int id_position) {
        this.id_position = id_position;
    }

    public String getPosition_name() {
        return position_name;
    }

    public void setPosition_name(String position_name) {
        this.position_name = position_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
