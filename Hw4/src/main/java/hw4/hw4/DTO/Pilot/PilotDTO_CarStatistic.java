package hw4.hw4.DTO.Pilot;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PilotDTO_CarStatistic {

    private Long pilotID;

    private String firstName;

    private String lastName;

    private Long numberOfCars;

}
