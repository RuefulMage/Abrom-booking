package ru.kpfu.itis.Transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

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
