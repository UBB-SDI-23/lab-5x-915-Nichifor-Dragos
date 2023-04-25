package hw4.hw4.Entity.User;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity // JPA annotation to make this object ready for storage in a JPA-based data store
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_profiles")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String bio;

    @Column
    private String location;

    @Column
    private Date birthdate;

    @Column(length = 20)
    private String gender;

    @Column(length = 20)
    private String maritalStatus;


}
