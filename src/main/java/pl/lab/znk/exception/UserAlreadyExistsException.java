package pl.lab.znk.exception;

public class UserAlreadyExistsException extends AlreadyExistsException {

    private Caused caused;
    private String identifier;

    public enum Caused {
        Login, Email
    }

    public UserAlreadyExistsException() {
        super("User already exists");
    }

    public UserAlreadyExistsException(Caused caused, String identifier) {
        super("User " + identifier + " already exists");
        this.caused = caused;
        this.identifier = identifier;
    }

    @Override
    public String getMessage() {
        switch (caused) {
            case Login:
                return "User with login " + identifier + " already exists";
            case Email:
                return "User with email " + identifier + " already exists";
        }

        return "";
    }

    public Caused getCaused() {
        return caused;
    }
}
