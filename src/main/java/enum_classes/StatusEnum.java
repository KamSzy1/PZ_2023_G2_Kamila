package enum_classes;

import lombok.Getter;

@Getter
public enum StatusEnum {
    DONE(0),
    UNDONE(1),
    IN_PROGRESS(2),
    OUTDATED(3);

    private final int index;

    StatusEnum(int index) {
        this.index = index;
    }

    static StatusEnum getStatusByIndex(final int index) {
        return StatusEnum.values()[index];
    }
}
