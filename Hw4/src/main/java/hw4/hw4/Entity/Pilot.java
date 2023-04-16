package hw4.hw4.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hw4.hw4.Entity.RacePilot.RacesPilots;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity // JPA annotation to make this object ready for storage in a JPA-based data store
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Pilots")
public class Pilot {

    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) Long id; // marked with more JPA annotations to indicate it’s the primary key and automatically populated by the JPA provide

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String nationality;

    private Date date;

    @Min(3)
    @Max(25)
    private Integer drivingExperience;

    @OneToMany(mappedBy = "pilot", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Car> cars;

    @OneToMany(mappedBy = "pilot", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<RacesPilots> racesPilots;

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Pilot))
            return false;
        Pilot pilot = (Pilot) o;
        return Objects.equals(this.id, pilot.id) && Objects.equals(this.firstName, pilot.firstName)
                && Objects.equals(this.lastName, pilot.lastName) && Objects.equals(this.nationality, pilot.nationality)
                && Objects.equals(this.date, pilot.date) && Objects.equals(this.drivingExperience, pilot.drivingExperience);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.firstName, this.lastName, this.nationality, this.date, this.drivingExperience);
    }

    @Override
    public String toString() {
        return "Pilot{" + "id=" + this.id + ", firstName='" + this.firstName + '\'' + ", lastName='" + this.lastName + '\'' +
                ", nationality='" + this.nationality + '\'' + ", date='" + this.date + '\'' +
                ", drivingExperience='" + this.drivingExperience + '\'' + '}';
    }

}
