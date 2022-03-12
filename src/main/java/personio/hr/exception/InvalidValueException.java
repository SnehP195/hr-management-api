package personio.hr.exception;

public class InvalidValueException extends RuntimeException {

    public InvalidValueException() {
        super("Input JSON of Employees contains empty/null value.");
    }

    public InvalidValueException(String employeeName) {
        super("Employee: " + employeeName + " has invalid value.");
    }
}
