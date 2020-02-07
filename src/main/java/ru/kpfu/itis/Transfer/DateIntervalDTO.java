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
    private Date start;

    @Future
    @NotNull
    private Date end;

    @NotNull
    private Long cottageID;

    private String status;

    private UserDTO user;

    private Long id;
}
