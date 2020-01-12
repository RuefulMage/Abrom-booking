package ru.kpfu.itis.Forms;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.Models.Enums.IntervalStatus;
import ru.kpfu.itis.Models.User;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DateIntervalForm {

    private Date startOfIntreval;
    private Date endOfInterval;

}
