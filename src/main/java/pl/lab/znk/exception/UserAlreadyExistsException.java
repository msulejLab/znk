package pl.lab.znk.exception;

public class UserAlreadyExistsException extends AlreadyExistsException {

    private Caused caused;

    public enum Caused {
        Login, Email
    }

    public UserAlreadyExistsException() {
        super("User already exists");
    }

    public UserAlreadyExistsException(Caused caused, String identifier) {
        super("User " + identifier + " already exists");
        this.caused = caused;
    }

    @Override
    public String getMessage() {
        switch (caused) {
            case Login:
                return "Login already exists";
            case Email:
                return "Email already exists";
        }

        return "";
    }
}
