package dev.drugowick.springauthorizationserverui.web.dto;

import dev.drugowick.springauthorizationserverui.web.dto.validation.NewPasswordFieldsMustMatch;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@NewPasswordFieldsMustMatch(fieldNewPassword = "newPassword", fieldNewPasswordConfirmation = "newPasswordConfirmation")
public class PasswordChange {

    private String currentPassword;

    @NotBlank
    @Size(min = 4, max = 60)
    private String newPassword;

    private String newPasswordConfirmation;
}
