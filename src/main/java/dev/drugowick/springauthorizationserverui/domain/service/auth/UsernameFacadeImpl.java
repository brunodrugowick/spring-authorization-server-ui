package dev.drugowick.springauthorizationserverui.domain.service.auth;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Implementation hiding the static code to get the Authentication (user info)
 */
@Component
public class UsernameFacadeImpl implements UsernameFacade {

    @Override
    public String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        }
        return "WHO THE HELL ARE YOU?";
    }
}
