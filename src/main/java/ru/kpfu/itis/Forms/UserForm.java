package ru.kpfu.itis.Forms;

import lombok.*;
import org.hibernate.validator.constraints.Email;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserForm {
    private String firstName;
    private String lastName;
    private String login;
    private String password;

    @Email
    private String email;

}
