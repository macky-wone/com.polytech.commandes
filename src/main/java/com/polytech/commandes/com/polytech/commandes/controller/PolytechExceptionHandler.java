

import com.polytech.commandes.com.polytech.commandes.service.PolytechApiError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

// Intentionally not a @ControllerAdvice to avoid springdoc incompatibility during /v3/api-docs
// Re-enable global exception handling after updating springdoc to a compatible version

public class PolytechExceptionHandler {

    @ExceptionHandler(PolytechApiError.class)
    @ResponseBody
    public ResponseEntity<?> handlePolytechApiError(PolytechApiError ex) {
        return ex.getResponse();
    }
}
