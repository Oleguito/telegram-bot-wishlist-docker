package application.service.statemachine;

import lombok.Getter;

@Getter
public enum State {
    IDLE("/idle"),
    START("/start"),
    ADD("/add"),
    SHOW_ALL("/showall"),
    FIND_BY_TITLE("/findbytitle"),
    DELETE("/delete"),
    HISTORY("/history");

    private String value;

    State(String value) {
        this.value = value;
    }
}
