package file_hierarchy.exceptions;

public class InvalidSortingException extends Exception {
    public InvalidSortingException() {
        super();
    }

    public InvalidSortingException(String message) {
        super(message);
    }

    public InvalidSortingException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidSortingException(Throwable cause) {
        super(cause);
    }
}
