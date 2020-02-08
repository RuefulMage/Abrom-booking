package ru.kpfu.itis.Forms;


import lombok.*;
import ru.kpfu.itis.Transfer.UserDTO;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
//TODO разобраться с формами и дто
public class DateIntervalForm {

    @Future
    @NotNull
    private Date startOfInterval;

    @Future
    @NotNull
    private Date endOfInterval;


    private Long CottageID;

    private String status;

    private UserDTO user;

    private Long id;

    public DateIntervalForm(Date startOfInterval, Date endOfInterval, Long cottageID) {
        this.startOfInterval = startOfInterval;
        this.endOfInterval = endOfInterval;
        CottageID = cottageID;
    }
}
