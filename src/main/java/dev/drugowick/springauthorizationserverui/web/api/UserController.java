package dev.drugowick.springauthorizationserverui.web.api;

import dev.drugowick.springauthorizationserverui.infrastructure.UserLoggerHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserLoggerHelper userLogger;

    @GetMapping("/oauth/user")
    public Map<String, Object> user (OAuth2Authentication user) {
        userLogger.log("Getting user info");
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("user", user.getUserAuthentication().getPrincipal());
        userInfo.put("authorities",
                AuthorityUtils.authorityListToSet(user.getUserAuthentication().getAuthorities()));

        return userInfo;
    }


}

