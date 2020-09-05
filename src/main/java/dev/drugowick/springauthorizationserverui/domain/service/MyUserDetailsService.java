package dev.drugowick.springauthorizationserverui.domain.service;

import dev.drugowick.springauthorizationserverui.domain.entity.MyUserDetails;
import dev.drugowick.springauthorizationserverui.domain.entity.User;
import dev.drugowick.springauthorizationserverui.domain.repository.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Primary
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<User> optionalEmployee = userRepository.findFirstByEmail(email);

        return optionalEmployee.map(MyUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Not found: " + email));
    }
}
