package com.developer.beverageapi.core.security.authorizationserver;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/oauth2")
public class AuthorizationConsentAndClientsController {

    private final RegisteredClientRepository registeredClientRepository;
    private final OAuth2AuthorizationConsentService consentService;
    private final OAuth2AuthorizationQueryService oAuth2AuthorizationQueryService;
    private final OAuth2AuthorizationService oAuth2AuthorizationService;

    @GetMapping("/consent")
    public String consent(
            Principal principal,
            Model model,
            @RequestParam(OAuth2ParameterNames.CLIENT_ID) String clientId,
            @RequestParam(OAuth2ParameterNames.SCOPE) String scope,
            @RequestParam(OAuth2ParameterNames.STATE) String state
    ) throws AccessDeniedException {
        RegisteredClient client = this.registeredClientRepository.findByClientId(clientId);

        if (client == null) {
            throw new AccessDeniedException(String.format("Client of %s was not found", clientId));
        }

        OAuth2AuthorizationConsent consent = this.consentService.findById(client.getId(), principal.getName());

        String[] scopeArray = StringUtils.delimitedListToStringArray(scope, " ");
        Set<String> scopesToApprove = new HashSet<>(Set.of(scopeArray));

        Set<String> approvedScopesPreviously;
        if (consent != null) {
            approvedScopesPreviously = consent.getScopes();
            approvedScopesPreviously.removeAll(approvedScopesPreviously);
        } else {
            approvedScopesPreviously = Collections.emptySet();
        }

        model.addAttribute("clientId", clientId);
        model.addAttribute("state", state);
        model.addAttribute("principalName", principal.getName());
        model.addAttribute("scopesToApprove", scopesToApprove);
        model.addAttribute("approvedScopesPreviously", approvedScopesPreviously);

        return "pages/approval";
    }

    @GetMapping("/authorized-clients")
    public String clientsList(Principal principal, Model model) {
        List<RegisteredClient> clients = oAuth2AuthorizationQueryService.registeredClientListWithConsent(principal.getName());

        model.addAttribute("clients", clients);
        return "pages/authorized-clients";
    }

    @GetMapping("/authorized-clients/revoke")
    public String revoke(Principal principal, Model model, @RequestParam(OAuth2ParameterNames.CLIENT_ID) String clientId) throws AccessDeniedException {

        RegisteredClient registeredClient = this.registeredClientRepository.findByClientId(clientId);

        if (registeredClient == null) {
            throw new AccessDeniedException(String.format("Client %s not found", clientId));
        }

        OAuth2AuthorizationConsent consent = this.consentService.findById(registeredClient.getId(), principal.getName());

        List<OAuth2Authorization> authorizations = this.oAuth2AuthorizationQueryService.authorizationList(
                principal.getName(), registeredClient.getId());

        if (consent != null) {
            this.consentService.remove(consent);
        }

        for (OAuth2Authorization authorization : authorizations) {
            this.oAuth2AuthorizationService.remove(authorization);
        }

        return "redirect:/oauth2/authorized-clients";
    }
}
