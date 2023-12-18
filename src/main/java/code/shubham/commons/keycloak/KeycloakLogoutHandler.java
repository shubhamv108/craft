package code.shubham.commons.keycloak;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

// @Slf4j
//// @Component
// public class KeycloakLogoutHandler implements LogoutHandler {
//
// private final RestTemplate restTemplate;
//
// public KeycloakLogoutHandler(final RestTemplate restTemplate) {
// this.restTemplate = restTemplate;
// }
//
// @Override
// public void logout(HttpServletRequest request, HttpServletResponse response,
// Authentication auth) {
// logoutFromKeycloak((OidcUser) auth.getPrincipal());
// }
//
// private void logoutFromKeycloak(OidcUser user) {
// final String endSessionEndpoint = user.getIssuer() + "/protocol/openid-connect/logout";
// final UriComponentsBuilder builder =
// UriComponentsBuilder.fromUriString(endSessionEndpoint)
// .queryParam("id_token_hint", user.getIdToken().getTokenValue());
//
// final ResponseEntity<String> logoutResponse =
// this.restTemplate.getForEntity(builder.toUriString(),
// String.class);
// if (logoutResponse.getStatusCode().is2xxSuccessful())
// log.info("Successfully logged out from Keycloak");
// else
// log.error("Could not propagate logout to Keycloak");
// }
//
// }