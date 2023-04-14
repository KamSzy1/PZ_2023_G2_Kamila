package enum_classes;

import lombok.Getter;

@Getter
public enum StatusEnum {
    ADMIN(0),
    MANAGER(1),
    EMPLOYEE(2);

    private final int index;

    StatusEnum(int index) {
        this.index = index;
    }

    static StatusEnum getStatusByIndex(final int index) {
        return StatusEnum.values()[index];
    }
}
