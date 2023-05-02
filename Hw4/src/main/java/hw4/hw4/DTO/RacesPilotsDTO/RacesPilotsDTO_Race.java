package hw4.hw4.DTO.RacesPilotsDTO;

import hw4.hw4.Entity.User.User;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RacesPilotsDTO_Race {

    Long pilotId;

    User user;

    Integer startPosition;

    Boolean needAccommodation;

}
