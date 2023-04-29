package hw4.hw4.Entity.RacePilot;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hw4.hw4.Entity.Pilot;
import hw4.hw4.Entity.Race;
import javax.persistence.*;

import hw4.hw4.Entity.User.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "RacesPilots")
public class RacesPilots {

    @EmbeddedId
    RacesPilotsKey id;

    @ManyToOne
    @MapsId("pilotId")
    @JsonIgnore
    @JoinColumn(name = "pilot_id")
    Pilot pilot;

    @ManyToOne
    @MapsId("raceId")
    @JsonIgnore
    @JoinColumn(name = "race_id")
    Race race;

    Integer startPosition;
    Boolean needAccommodation;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.pilot, this.race, this.startPosition, this.needAccommodation);
    }

    @Override
    public String toString() {
        return "PilotRace{" + "id='" + this.id + ", startPosition='" + this.startPosition + '\'' + ", needAccommodation='" + this.needAccommodation + '\'' +'}';
    }

}
