package service.sessions;

public class SessionManager {
    
    static SessionManager instance = null;
    
    public static SessionManager getInstance() {
        if(instance == null) {
            return new SessionManager();
        }
        return instance;
    }
    
    public void manageSession (String callData, Long chatID) {
    
    }
}
