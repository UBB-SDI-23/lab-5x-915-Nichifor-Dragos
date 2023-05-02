package hw4.hw4.DTO.Race;

import hw4.hw4.DTO.RacesPilotsDTO.RacesPilotsDTO_Race;
import hw4.hw4.Entity.User.User;
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

    String username;

}
