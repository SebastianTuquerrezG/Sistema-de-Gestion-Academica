package unicauca.edu.co.auth_service.application.ports;

import unicauca.edu.co.auth_service.application.dto.request.AuthRequest;
import unicauca.edu.co.auth_service.application.dto.response.TokenResponse;

public interface AuthService {
    TokenResponse login(AuthRequest authRequest);
    boolean logout(String refreshToken);
}
