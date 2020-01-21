package ru.kpfu.itis.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import ru.kpfu.itis.Models.Enums.Role;
import ru.kpfu.itis.Models.Enums.State;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "abrom_user")
//TODO Сделать user подходящим для контекста
//TODO Добавить валидацию


public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String login;
    private String hashPassword;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Enumerated(value = EnumType.STRING)
    private State state;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<DateInterval> dateIntervals;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Token> tokens;
}