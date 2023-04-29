package hw4.hw4.DTO.Race;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RaceDTO_All extends RaceDTO {

    private Long numberOfParticipants;

    private String username;

}
