package hw4.hw4.DTO.Race;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class RaceDTO {

    private Long id;
    private String name;
    private String country;
    private Integer numberOfLaps;
    private Integer lapLength;
    private Date date;

}
