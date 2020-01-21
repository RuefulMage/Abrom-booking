package ru.kpfu.itis.Forms;

import lombok.*;

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

}
