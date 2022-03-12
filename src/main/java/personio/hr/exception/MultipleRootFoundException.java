package personio.hr.exception;

import java.util.List;

public class MultipleRootFoundException extends RuntimeException {

    public MultipleRootFoundException(List<String> topSupervisors) {
        super("Multiple supervisors found: " + String.join(", ", topSupervisors));
    }
}
