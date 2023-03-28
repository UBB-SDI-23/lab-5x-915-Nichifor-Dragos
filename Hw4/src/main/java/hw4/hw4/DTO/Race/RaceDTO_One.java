package hw4.hw4.DTO.Race;

import hw4.hw4.DTO.RacesPilotsDTO.RacesPilotsDTO_Pilot;
import hw4.hw4.DTO.RacesPilotsDTO.RacesPilotsDTO_Race;
import lombok.*;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RaceDTO_One extends RaceDTO{
    Set<RacesPilotsDTO_Race> pilots;
}
