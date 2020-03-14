package ru.kpfu.itis.Transfer;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CottageDTO {

    private Long id;

    @NotNull
    private String description;

    @NotNull
    private String address;

    private List<DateIntervalDTO> dateIntervalDTOList;

    @NotNull
    private Map<String, Integer> rooms;

    private String wishes;
}
