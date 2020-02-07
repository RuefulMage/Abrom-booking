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
@EqualsAndHashCode
@ToString
@Builder
@Entity
@Table(name = "date_interval")
public class DateInterval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "startOfInterval", nullable = false)
    private Date startOfInterval;

    @Temporal(TemporalType.DATE)
    @Column(name = "endOfInterval", nullable = false)
    private Date endOfInterval;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "intervalStatus", nullable = false)
    private IntervalStatus intervalStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User owner;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cottage_id")
    @JsonBackReference
    private Cottage cottage;
}
