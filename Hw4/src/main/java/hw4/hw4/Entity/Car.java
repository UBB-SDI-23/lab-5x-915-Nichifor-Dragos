package hw4.hw4.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import hw4.hw4.Entity.User.User;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cars")
public class Car {

    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    @NotEmpty
    @Column
    private String brand;

    @NotEmpty
    @Column
    private String motorization;

    @NotEmpty
    @Column
    private String gearBox;

    @Min(2000)
    @Max(8000)
    @Column
    private Integer cylindricalCapacity;

    @Min(150)
    @Max(800)
    @Column
    private Integer horsePower;

    @NotEmpty
    @Column(length = 2000)
    private String description;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "pilot_id", nullable = false)
    private Pilot pilot;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Car))
            return false;
        Car car = (Car) o;
        return Objects.equals(this.id, car.id) && Objects.equals(this.brand, car.brand)
                && Objects.equals(this.motorization, car.motorization) && Objects.equals(this.gearBox, car.gearBox)
                && Objects.equals(this.cylindricalCapacity, car.cylindricalCapacity) && Objects.equals(this.horsePower, car.horsePower);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.brand, this.motorization, this.gearBox, this.cylindricalCapacity, this.horsePower);
    }

    @Override
    public String toString() {
        return "Car{" + "id=" + this.id + ", brand='" + this.brand + '\'' + ", motorization='" + this.motorization + '\'' +
                ", gearBox='" + this.gearBox + '\'' + ", cylindricalCapacity='" + this.cylindricalCapacity + '\'' +
                ", horsePower='" + this.horsePower + '\'' + "pilotId=" + this.pilot.getId() + ", userId='" + this.user.getId() + '\'' + '}';
    }

}
