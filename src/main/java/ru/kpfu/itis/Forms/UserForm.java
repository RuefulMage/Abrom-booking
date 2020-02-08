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
public class UserForm {

    private String password;

    @JsonProperty(value = "firstname")
    private String firstName;

    @JsonProperty(value = "lastname")
    private String lastName;

    @JsonProperty("login")
    private String login;

    @JsonProperty(value = "email")
    @Email
    private String email;

}
