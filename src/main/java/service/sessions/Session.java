package service.sessions;

import lombok.Builder;
import lombok.Getter;
import service.statemachine.State;

@Builder
@Getter
public class Session {
    private Long sessionID;
    private State state;
}
