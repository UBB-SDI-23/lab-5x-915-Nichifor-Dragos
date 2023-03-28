package hw4.hw4.DTO.Race;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RaceDTO_PilotStatistic {
    private Long raceID;
    private String name;
    private Long numberOfPilots;
}
