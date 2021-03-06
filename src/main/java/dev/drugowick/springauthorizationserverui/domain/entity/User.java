package dev.drugowick.springauthorizationserverui.domain.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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
    private String password;

    private String roles;
    private boolean enabled = true;
}
