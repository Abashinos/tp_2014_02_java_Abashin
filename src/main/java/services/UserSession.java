package services;

public class UserSession {

    private String sessionId;
    private Long userId;
    private String username;
    private String errorMessage;
    private Status status;

    public enum Status {
        ERROR,
        IN_PROGRESS,
        OK
    }

    public UserSession(String sessionId, Long userId, String username, Status status) {
        this(sessionId, userId, username, null, status);
    }
    public UserSession(String sessionId, Long userId, String username, String errorMessage, Status status) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.username = username;
        this.errorMessage = errorMessage;
        this.status = status;
    }

    public String getSessionId() {
        return sessionId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Status getStatus() {
        return status;
    }

    public void setUserSession(UserSession session) {
        this.sessionId = session.getSessionId();
        this.userId = session.getUserId();
        this.username = session.getUsername();
        this.errorMessage = session.getErrorMessage();
        this.status = session.getStatus();
    }

    public static UserSession getUserSession(String sessionId, String username) {
        return new UserSession(sessionId, null, username, null, null);
    }

    public static UserSession getUserSessionError(String sessionId, String username, String errorMessage) {
        return new UserSession(sessionId, null, username, errorMessage, Status.ERROR);
    }
}
