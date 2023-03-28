package hw4.hw4.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hw4.hw4.Entity.RacePilot.RacesPilots;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity  // JPA annotation to make this object ready for storage in a JPA-based data store
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Races")
public class Race {

    private @Id
    @GeneratedValue Long id; // marked with more JPA annotations to indicate it’s the primary key and automatically populated by the JPA provide

    @NotEmpty
    private String name;

    @NotEmpty
    private String country;

    private Integer numberOfLaps;

    private Integer lapLength;

    private Date date;

    @OneToMany(mappedBy = "race")
    @JsonIgnore
    Set<RacesPilots> racesPilots;

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Pilot))
            return false;
        Race race = (Race) o;
        return Objects.equals(this.id, race.id) && Objects.equals(this.name, race.name)
                && Objects.equals(this.country, race.country) && Objects.equals(this.numberOfLaps, race.numberOfLaps)
                && Objects.equals(this.lapLength, race.lapLength) && Objects.equals(this.date, race.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.country, this.numberOfLaps, this.lapLength, this.date);
    }

    @Override
    public String toString() {
        return "Race{" + "id=" + this.id + ", name='" + this.name + '\'' + ", country='" + this.country + '\'' +
                ", numberOfLaps='" + this.numberOfLaps + '\'' + ", lapLength='" + this.lapLength + '\'' +
                ", date='" + this.date + '\'' + '}';
    }

}
