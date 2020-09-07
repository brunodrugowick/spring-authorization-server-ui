package dev.drugowick.springauthorizationserverui.web.api;

import dev.drugowick.springauthorizationserverui.domain.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClientController {

    private final ClientRepository clientRepository;
    private final ClientDetailsService clientDetailsService;

    @GetMapping("/oauth/clients")
    public List<ClientDetails> getAllClients() {
        List<ClientDetails> clients = new ArrayList<>();
        clientRepository.findAll().forEach(client -> clients.add(clientDetailsService.loadClientByClientId(client.getClientId())));
        return clients;
    }

    @GetMapping("/oauth/clients/{clientId}")
    public ResponseEntity<Object> getClient(@PathVariable String clientId) {
        try {
            return ResponseEntity.ok(clientDetailsService.loadClientByClientId(clientId));
        } catch (ClientRegistrationException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}
