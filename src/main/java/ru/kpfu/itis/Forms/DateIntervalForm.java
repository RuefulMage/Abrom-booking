package ru.kpfu.itis.Forms;


import lombok.*;
import ru.kpfu.itis.Models.Enums.IntervalStatus;
import ru.kpfu.itis.Models.User;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DateIntervalForm {

    private Date startOfIntreval;
    private Date endOfInterval;

}
