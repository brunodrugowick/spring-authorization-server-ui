package dev.drugowick.springauthorizationserverui.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.anonymous().disable()
                .requestMatcher(request -> {
                    var auth = request.getHeader(HttpHeaders.AUTHORIZATION);
                    var uri = request.getRequestURI();
                    return (auth != null && auth.startsWith("Bearer")) || uri.startsWith("/oauth");
                })
        .authorizeRequests().anyRequest().authenticated();
    }
}
