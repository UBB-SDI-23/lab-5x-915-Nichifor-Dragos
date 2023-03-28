package hw4.hw4.DTO.RacesPilotsDTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RacesPilotsDTO_Race {
    Long pilotId;
    Integer startPosition;
    Boolean needAccommodation;
}
