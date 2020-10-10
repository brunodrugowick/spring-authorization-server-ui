package dev.drugowick.springauthorizationserverui.infrastructure;

import org.springframework.security.core.Authentication;

/**
 * A facade to provide user info to the application
 */
public interface AuthenticationFacade {
    Authentication getAuthentication();
}
