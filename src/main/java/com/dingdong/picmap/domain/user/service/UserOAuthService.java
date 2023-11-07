package com.dingdong.picmap.domain.user.service;

import com.dingdong.picmap.domain.user.entity.UserResource;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserOAuthService {

    private final Environment env;
    private final RestTemplate restTemplate = new RestTemplate();

    public void socialLogin(String code, String registrationId) {
        String accessToken = getAccessToken(code, registrationId);
        log.info("code: {}, registrationId: {}", code, registrationId);
        log.info("accessToken: {}", accessToken);

        JsonNode userResourceNode = getUserResource(accessToken, registrationId);
        log.info("userResourceNode: {}", userResourceNode);

        UserResource userResource = new UserResource(
                userResourceNode.get("id").asText(),
                userResourceNode.get("email").asText(),
                userResourceNode.get("nickname").asText()
        );
    }

    private String getAccessToken(String authorizationCode, String registrationId) {
        String clientId = env.getProperty("oauth2." + registrationId + ".client-id");
        String clientSecret = env.getProperty("oauth2." + registrationId + ".client-secret");
        String redirectUri = env.getProperty("oauth2." + registrationId + ".redirect-uri");
        String tokenUri = env.getProperty("oauth2." + registrationId + ".token-uri");

        log.info("clientId: {}, clientSecret: {}, redirectUri: {}, tokenUri: {}", clientId, clientSecret, redirectUri, tokenUri);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", authorizationCode);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity entity = new HttpEntity(params, headers);

        ResponseEntity<JsonNode> jsonResponse = restTemplate.exchange(tokenUri, HttpMethod.POST, entity, JsonNode.class);
        JsonNode accessTokenNode = jsonResponse.getBody().get("access_token");
        return accessTokenNode.asText();
    }

    private JsonNode getUserResource(String accessToken, String registrationId) {
        String resourceUri = env.getProperty("oauth2." + registrationId + ".resource-uri");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity entity = new HttpEntity(headers);
        return restTemplate.exchange(resourceUri, HttpMethod.GET, entity, JsonNode.class).getBody();
    }
}
