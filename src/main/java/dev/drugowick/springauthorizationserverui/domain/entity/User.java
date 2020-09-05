package dev.drugowick.springauthorizationserverui.domain.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity(name = "user_table")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 4, max = 60, message = "Senha deve conter no mínimo 4 e no máximo 60 caracteres")
    private String password;

    private String roles;
    private boolean enabled = true;
}
