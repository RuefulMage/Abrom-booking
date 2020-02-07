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
@EqualsAndHashCode
@ToString(exclude = {"dateIntervals", "tokens"})
@Entity
@Table(name = "abrom_user")
//TODO Сделать user подходящим для контекста
//TODO Добавить валидацию
//TODO Добавить Email

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname", nullable = false)
    private String firstName;

    @Column(name = "lastname", nullable = false)
    private String lastName;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "hashpassword", nullable = false)
    private String hashPassword;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "state", nullable = false)
    private State state;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<DateInterval> dateIntervals;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Token> tokens;
}