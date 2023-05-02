package hw4.hw4.DTO.RacesPilotsDTO;

import hw4.hw4.Entity.Pilot;
import hw4.hw4.Entity.Race;
import hw4.hw4.Entity.RacePilot.RacesPilotsKey;
import hw4.hw4.Entity.User.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
public class RacesPilotsDTO_One {

    RacesPilotsKey id;

    Pilot pilot;

    Race race;

    Integer startPosition;

    Boolean needAccommodation;

    User user;

}
