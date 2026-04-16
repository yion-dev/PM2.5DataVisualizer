package exceptions;

public class ApiResponseException extends ApiException {

    public ApiResponseException(String message) {
        super(message);
    }

    public ApiResponseException(String message, Throwable cause) {
        super(message, cause);
    }

}
