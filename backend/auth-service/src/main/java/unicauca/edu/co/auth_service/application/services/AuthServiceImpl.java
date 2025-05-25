package unicauca.edu.co.auth_service.application.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import unicauca.edu.co.auth_service.application.dto.request.AuthRequest;
import unicauca.edu.co.auth_service.application.dto.response.TokenResponse;
import unicauca.edu.co.auth_service.application.ports.AuthService;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Value("${keycloak.token-uri}")
    private String tokenUrl;

    @Value("${keycloak.logout-uri}")
    private String logoutUrl;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    @Override
    public TokenResponse login(AuthRequest authRequest) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        params.put("username", authRequest.getUsername());
        params.put("password", authRequest.getPassword());
        params.put("grant_type", "password");
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);

        String body = params.entrySet()
                .stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .reduce((a, b) -> a + "&" + b)
                .orElse("");

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        var entity = new HttpEntity<>(body, headers);

        var response = restTemplate.postForEntity(tokenUrl, entity, String.class);

        try{
            return new ObjectMapper().readValue(response.getBody(), TokenResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing token response", e);
        }
    }

    @Override
    public boolean logout(String refreshToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = "client_id=" + clientId +
                "&client_secret=" + clientSecret +
                "&refresh_token=" + refreshToken;

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(logoutUrl, entity, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }
    }
}
