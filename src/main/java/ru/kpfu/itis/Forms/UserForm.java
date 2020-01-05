package ru.kpfu.itis.Forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserForm {
    private String firstName;
    private String lastName;
    private String login;
    private String password;

}
