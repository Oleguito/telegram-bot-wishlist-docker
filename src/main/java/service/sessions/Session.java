package service.sessions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import service.statemachine.State;

@Builder
@Getter
@Setter
public class Session {
    private Long sessionID;
    private State state;
}
