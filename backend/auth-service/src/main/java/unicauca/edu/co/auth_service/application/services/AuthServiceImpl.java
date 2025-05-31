package unicauca.edu.co.auth_service.application.services;

import com.fasterxml.jackson.databind.JsonNode;
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

import java.util.*;

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

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, entity, String.class);

        try{
            ObjectMapper mapper = new ObjectMapper();
            JsonNode tokenJson = mapper.readTree(response.getBody());

            String accessToken = tokenJson.get("access_token").asText();
            String refreshToken = tokenJson.get("refresh_token").asText();
            Long expiresIn = tokenJson.get("expires_in").asLong();

            String[] tokenParts = accessToken.split("\\.");
            String payload = new String(Base64.getDecoder().decode(tokenParts[1]));

            JsonNode payloadJson = mapper.readTree(payload);

            String username = payloadJson.has("preferred_username") ? payloadJson.get("preferred_username").asText() : null;
            String email = payloadJson.has("email") ? payloadJson.get("email").asText() : null;
            String userId = payloadJson.has("sub") ? payloadJson.get("sub").asText() : null;

            List<String> roles = new ArrayList<>();
            if (payloadJson.has("realm_access")) {
                JsonNode realmAccess = payloadJson.get("realm_access");
                if (realmAccess.has("roles")) {
                    realmAccess.get("roles").forEach(role -> roles.add(role.asText()));
                }
            }

            return TokenResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .expiresIn(expiresIn)
                    .userId(userId)
                    .username(username)
                    .email(email)
                    .roles(roles)
                    .build();
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
