package dev.drugowick.springauthorizationserverui.domain.service.auth;

/**
 * A facade to provide user info to the application
 *
 * bY https://www.baeldung.com/get-user-in-spring-security
 */
public interface UsernameFacade {
    String getUsername();
}
