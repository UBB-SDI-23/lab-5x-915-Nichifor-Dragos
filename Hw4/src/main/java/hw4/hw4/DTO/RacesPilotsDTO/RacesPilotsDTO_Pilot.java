package hw4.hw4.DTO.RacesPilotsDTO;

import hw4.hw4.Entity.User.User;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RacesPilotsDTO_Pilot {

    Long raceId;

    User user;

    Integer startPosition;

    Boolean needAccommodation;

}
