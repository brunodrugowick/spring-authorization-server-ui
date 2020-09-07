package dev.drugowick.springauthorizationserverui.domain.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.*;
import java.util.stream.Collectors;

public class MyClientDetails implements ClientDetails {

    private String clientId;
    private String clientSecret;
    private Set<String> scopes;
    private Set<String> authorizedGrantTypes;

    public MyClientDetails(Client client) {
        this.clientId = client.getClientId();
        this.clientSecret = client.getClientSecret();
        this.scopes = Arrays.stream(client.getScopes().split(","))
                .map(String::trim)
                .collect(Collectors.toSet());
        this.authorizedGrantTypes = Arrays.stream(client.getGrantTypes().split(","))
                .map(String::trim)
                .collect(Collectors.toSet());
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public Set<String> getResourceIds() {
        return Collections.emptySet();
    }

    @Override
    public boolean isSecretRequired() {
        return true;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    @Override
    public boolean isScoped() {
        return true;
    }

    @Override
    public Set<String> getScope() {
        return scopes;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return Collections.emptySet();
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return null;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return null;
    }

    @Override
    public boolean isAutoApprove(String s) {
        return false;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }
}
