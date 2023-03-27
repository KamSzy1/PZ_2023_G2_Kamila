package enum_classes;

import lombok.Getter;

@Getter
public enum PositionEnum {
    ADMIN(0),
    MANAGER(1),
    EMPLOYEE(2);

    private final int index;

    PositionEnum(int index) {
        this.index = index;
    }

    static PositionEnum getPositionByIndex(final int index){
        return PositionEnum.values()[index];
    }
}
