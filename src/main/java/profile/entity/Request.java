package profile.entity;

import login.entity.User;

public class Request {
    private RequestType type;
    private User user;
    private User supervisor;
    private Action action;

    public Request() {
    }

    public Request(RequestType type, User user, User supervisor, Action action) {
        this.type = type;
        this.user = user;
        this.supervisor = supervisor;
        this.action = action;
    }

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(User supervisor) {
        this.supervisor = supervisor;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
