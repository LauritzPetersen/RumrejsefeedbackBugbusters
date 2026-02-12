package Service;

public class EngineFailureException extends RuntimeException {
    public EngineFailureException(String message) {
        super(message);
    }
}
