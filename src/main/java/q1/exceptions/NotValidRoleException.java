package q1.exceptions;

public class NotValidRoleException extends Exception{
    public NotValidRoleException() {
    }

    public NotValidRoleException(String message) {
        super(message);
    }
}
