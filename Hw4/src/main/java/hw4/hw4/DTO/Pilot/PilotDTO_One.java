package hw4.hw4.DTO.Pilot;

import hw4.hw4.DTO.Car.CarDTO_All;

import hw4.hw4.DTO.RacesPilotsDTO.RacesPilotsDTO_Pilot;
import hw4.hw4.Entity.User.User;
import lombok.*;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PilotDTO_One extends PilotDTO {

    private Set<CarDTO_All> cars;

    private Set<RacesPilotsDTO_Pilot> races;

    private String username;

}
