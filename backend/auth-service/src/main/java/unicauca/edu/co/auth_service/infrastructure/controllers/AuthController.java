package unicauca.edu.co.auth_service.infrastructure.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unicauca.edu.co.auth_service.application.dto.request.AuthRequest;
import unicauca.edu.co.auth_service.application.dto.request.LogoutRequest;
import unicauca.edu.co.auth_service.application.dto.response.TokenResponse;
import unicauca.edu.co.auth_service.application.ports.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.login(authRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody LogoutRequest request) {
        boolean success = authService.logout(request.getRefreshToken());
        return success
                ? ResponseEntity.ok("Logout exitoso")
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al cerrar sesión");
    }
}
