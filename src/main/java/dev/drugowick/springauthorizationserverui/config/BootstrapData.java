package dev.drugowick.springauthorizationserverui.config;

import dev.drugowick.springauthorizationserverui.config.entity.Config;
import dev.drugowick.springauthorizationserverui.config.entity.ConfigRepository;
import dev.drugowick.springauthorizationserverui.domain.entity.Client;
import dev.drugowick.springauthorizationserverui.domain.entity.User;
import dev.drugowick.springauthorizationserverui.domain.repository.ClientRepository;
import dev.drugowick.springauthorizationserverui.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    public static final String FIRST_RUN_CONFIG_NAME = "firstRun";
    public static final String FIRST_RUN_CONFIG_VALUE = "true";

    private final ConfigRepository configRepository;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Optional<Config> firstRunConfig = configRepository.findByName(FIRST_RUN_CONFIG_NAME);

        if (!firstRunConfig.isPresent()) {
            loadDemoData();

            Config firstRun = new Config();
            firstRun.setName(FIRST_RUN_CONFIG_NAME);
            firstRun.setValue("false");
            configRepository.save(firstRun);

            return;
        }

        if (firstRunConfig.get().getValue().equals(FIRST_RUN_CONFIG_VALUE)) {
            loadDemoData();

            Config existingFirstRunConfig = firstRunConfig.get();
            existingFirstRunConfig.setValue("false");
            configRepository.save(existingFirstRunConfig);
        }
    }

    private void loadDemoData() {
        User user1 = new User();
        user1.setEmail("user@email.com");
        user1.setPassword(passwordEncoder.encode("password"));
        user1.setRoles("USER");
        user1.setEnabled(true);
        userRepository.save(user1);

        User user2 = new User();
        user2.setEmail("admin@email.com");
        user2.setPassword(passwordEncoder.encode("password"));
        user2.setRoles("USER, ADMIN");
        user2.setEnabled(true);
        userRepository.save(user2);

        Client client1 = new Client();
        client1.setClientId("client");
        client1.setClientSecret(passwordEncoder.encode("password"));
        client1.setGrantTypes("refresh_token, password, client_credentials");
        client1.setScopes("web, arrobas, saladas");
        clientRepository.save(client1);

        Client client2 = new Client();
        client2.setClientId("client2");
        client2.setClientSecret(passwordEncoder.encode("password"));
        client2.setGrantTypes("password");
        client2.setScopes("web");
        clientRepository.save(client2);
    }
}
