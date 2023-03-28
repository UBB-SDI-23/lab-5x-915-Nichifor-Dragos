package hw4.hw4.DTO.Pilot;

import hw4.hw4.DTO.Car.CarDTO_All;

import hw4.hw4.DTO.RacesPilotsDTO.RacesPilotsDTO_Pilot;
import lombok.*;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PilotDTO_One extends PilotDTO {
    Set<CarDTO_All> cars;
    Set<RacesPilotsDTO_Pilot> races;
}
