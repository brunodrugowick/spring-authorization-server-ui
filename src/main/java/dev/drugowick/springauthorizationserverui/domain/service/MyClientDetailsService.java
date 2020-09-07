package dev.drugowick.springauthorizationserverui.domain.service;

import dev.drugowick.springauthorizationserverui.domain.entity.Client;
import dev.drugowick.springauthorizationserverui.domain.entity.MyClientDetails;
import dev.drugowick.springauthorizationserverui.domain.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Primary
@RequiredArgsConstructor
public class MyClientDetailsService implements ClientDetailsService {

    private final ClientRepository clientRepository;

    @Override
    public ClientDetails loadClientByClientId(String s) {
        Optional<Client> optionalClient = clientRepository.findFirstByClientId(s);

        return optionalClient.map(MyClientDetails::new)
                .orElseThrow(() -> new ClientRegistrationException("Could not find client: " + s));
    }

}
