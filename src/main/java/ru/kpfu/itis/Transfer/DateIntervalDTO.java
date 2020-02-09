package ru.kpfu.itis.Transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DateIntervalDTO {

    @Future
    @NotNull
    private Date startOfInterval;

    @Future
    @NotNull
    private Date endOfInterval;

    @NotNull
    private Long cottageID;

    private String intervalStatus;
    private UserDTO owner;
    private Long id;
}