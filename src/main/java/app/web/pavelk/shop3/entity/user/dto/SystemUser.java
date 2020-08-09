package app.web.pavelk.shop3.entity.user.dto;


import app.web.pavelk.shop3.utils.validation.FieldMatch;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
// своя анотация для сверки пороля
@FieldMatch(first = "password", second = "matchingPassword", message = "The password fields must match")
public class SystemUser {

    @NotNull(message = "phone number is required")
    @Size(min = 8, message = "phone number length must be 8")
    private String phone;

    @NotNull(message = "is required")
    @Size(min = 4, message = "password is too short")
    private String password;

    @NotNull(message = "is required")
    @Size(min = 4, message = "password is too short")
    private String matchingPassword;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String firstName;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String lastName;

    @NotNull(message = "is required")
    @Size(min = 4, message = "is required")
    @Email
    private String email;
}
