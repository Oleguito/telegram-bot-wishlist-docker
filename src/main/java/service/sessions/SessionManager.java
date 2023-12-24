package service.sessions;

import org.apache.http.io.SessionInputBuffer;
import service.statemachine.State;

import java.util.Map;

public class SessionManager {
    
    static SessionManager instance = null;
    private Map <Long, Session> userToSession;
    
    public static SessionManager getInstance() {
        if(instance == null) {
            return new SessionManager();
        }
        return instance;
    }
    
    public void manageSession (String callData, Long chatID) {
        if(!userToSession.containsKey(chatID)) {
            throw new RuntimeException("Такого юзера нету в мапе класса");
        }
        
        switch (userToSession.get(chatID).getState()) {
            case IDLE -> {
            
            }
        }
        
    }
    
    public void createSession(Long chatID) {
        userToSession.put(chatID,
                Session.builder()
                        
                        .build());
    }
    
    public Session getSession(Long chatID) {
        return userToSession.get(chatID);
    }
}
