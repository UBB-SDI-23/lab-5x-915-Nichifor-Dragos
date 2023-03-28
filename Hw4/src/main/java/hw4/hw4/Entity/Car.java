package hw4.hw4.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Objects;

@Entity // JPA annotation to make this object ready for storage in a JPA-based data store
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Cars")
public class Car {

    private @Id @GeneratedValue Long id; // marked with more JPA annotations to indicate it’s the primary key and automatically populated by the JPA provide\

    @NotEmpty
    private String brand;

    @NotEmpty
    private String motorization;

    @NotEmpty
    private String gearBox;

    @Min(2000)
    @Max(8000)
    private Integer cylindricalCapacity;

    @Min(150)
    @Max(800)
    private Integer horsePower;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "pilot_id", nullable = false)
    private Pilot pilot;

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
                ", horsePower='" + this.horsePower + '\'' + '}';
    }

}
