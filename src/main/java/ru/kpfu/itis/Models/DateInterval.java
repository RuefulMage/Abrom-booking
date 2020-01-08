package ru.kpfu.itis.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.Models.Enums.IntervalStatus;

import javax.persistence.*;
import java.util.Date;

@Data
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
//    @Basic(fetch = FetchType.LAZY)
    private User owner;
}
