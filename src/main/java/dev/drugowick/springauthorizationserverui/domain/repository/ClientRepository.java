package dev.drugowick.springauthorizationserverui.domain.repository;

import dev.drugowick.springauthorizationserverui.domain.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findFirstByClientId(String clientId);

}
