package pl.lab.znk.exception;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException() {
        super("User was not found");
    }

    public UserNotFoundException(Long id) {
        super("User with id " + id + " was not found");
    }

    public UserNotFoundException(String identifier) {
        super("User " + identifier + " was not found");
    }
}
