package ru.kpfu.itis.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cottage")
public class Cottage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String address;

    private String description;

    @OneToMany(mappedBy = "cottage", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<DateInterval> dateIntervals;
}
