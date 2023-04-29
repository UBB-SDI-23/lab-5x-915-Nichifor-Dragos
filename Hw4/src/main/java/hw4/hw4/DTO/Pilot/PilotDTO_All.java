package hw4.hw4.DTO.Pilot;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PilotDTO_All extends PilotDTO {

    private Long numberOfRaces;

    private String username;

}
