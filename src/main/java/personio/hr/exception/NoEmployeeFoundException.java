package personio.hr.exception;

public class NoEmployeeFoundException extends RuntimeException {

    public NoEmployeeFoundException() {
        super("Employee not found");
    }

    public NoEmployeeFoundException(String message) {
        super(message);
    }
}
