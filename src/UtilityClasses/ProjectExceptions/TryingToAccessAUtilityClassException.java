package UtilityClasses.ProjectExceptions;

public class TryingToAccessAUtilityClassException extends RuntimeException {
    public TryingToAccessAUtilityClassException(String message) {
        super(message);
    }
}
