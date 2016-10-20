package pl.lab.znk.exception;

public class ConsultationAlreadyExistsException extends AlreadyExistsException{
    private Caused caused;

    public enum Caused {
        Time
    }

    public ConsultationAlreadyExistsException() {
        super("Consultation already exists");
    }

    public ConsultationAlreadyExistsException(Caused caused, String identifier) {
        super("Consultation " + identifier + " already exists");
        this.caused = caused;
    }

    @Override
    public String getMessage() {
        switch (caused) {
            case Time:
                return "Consultation on that time already exists";
        }

        return "";
    }
}
