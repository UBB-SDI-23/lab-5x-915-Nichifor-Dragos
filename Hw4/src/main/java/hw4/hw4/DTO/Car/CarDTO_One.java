package hw4.hw4.DTO.Car;

import hw4.hw4.Entity.Pilot;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CarDTO_One extends CarDTO {

    Pilot pilot;

}
