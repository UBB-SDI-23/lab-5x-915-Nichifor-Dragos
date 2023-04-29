package hw4.hw4.DTO.Pilot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class PilotDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String nationality;

    private Date date;

    private Integer drivingExperience;

}
