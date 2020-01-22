package ru.kpfu.itis.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import ru.kpfu.itis.Models.Enums.IntervalStatus;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "date_interval")
public class DateInterval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date startOfInterval;

    @Temporal(TemporalType.DATE)
    private Date endOfInterval;

    @Enumerated(value = EnumType.STRING)
    private IntervalStatus intervalStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User owner;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cottage_id")
    @JsonBackReference
    private User cottage;
}
