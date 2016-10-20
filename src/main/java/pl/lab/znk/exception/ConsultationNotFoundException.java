package pl.lab.znk.exception;

public class ConsultationNotFoundException extends NotFoundException{
    public ConsultationNotFoundException() {
        super("Consultation was not found");
    }

    public ConsultationNotFoundException(Long id) {
        super("Consultation with id " + id + " was not found");
    }

    public ConsultationNotFoundException(String identifier) {
        super("Consultation " + identifier + " was not found");
    }
}
