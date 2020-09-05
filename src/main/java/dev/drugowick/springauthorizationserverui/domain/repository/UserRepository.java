package dev.drugowick.springauthorizationserverui.domain.repository;

import dev.drugowick.springauthorizationserverui.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findFirstByEmail(String email);
}
