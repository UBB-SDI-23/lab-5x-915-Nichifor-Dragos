package hw4.hw4.DTO.Car;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CarDTO_All extends CarDTO {

    private Long pilotID;

}
