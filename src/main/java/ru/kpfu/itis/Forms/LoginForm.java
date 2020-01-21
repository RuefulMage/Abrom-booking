package ru.kpfu.itis.Forms;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginForm {
    private String login;
    private String password;
}
