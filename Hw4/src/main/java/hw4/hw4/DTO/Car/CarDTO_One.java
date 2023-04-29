package hw4.hw4.DTO.Car;

import hw4.hw4.Entity.Pilot;
import hw4.hw4.Entity.User.User;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CarDTO_One extends CarDTO {

    private Pilot pilot;

    private User user;

}
