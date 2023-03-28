package hw4.hw4.DTO.Car;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class CarDTO {

    private Long id;
    private String brand;
    private String motorization;
    private String gearBox;
    private Integer cylindricalCapacity;
    private Integer horsePower;

}
