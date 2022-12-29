package file_hierarchy.exceptions;

public class InvalidRequirementException extends Exception {
    public InvalidRequirementException() {
        super();
    }

    public InvalidRequirementException(String message) {
        super(message);
    }

    public InvalidRequirementException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidRequirementException(Throwable cause) {
        super(cause);
    }
}
