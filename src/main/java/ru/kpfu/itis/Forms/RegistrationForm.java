package ru.kpfu.itis.Forms;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationForm {

    @NotBlank
    private String password;

    @NotBlank
    @JsonProperty(value = "firstname")
    private String firstName;

    @NotBlank
    @JsonProperty(value = "lastname")
    private String lastName;

    @NotBlank
    @JsonProperty("login")
    private String login;

    @NotBlank
    @JsonProperty(value = "email")
    @Email
    private String email;

}
