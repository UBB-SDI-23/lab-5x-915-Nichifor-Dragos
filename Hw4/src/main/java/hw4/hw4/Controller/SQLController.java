package hw4.hw4.Controller;

import hw4.hw4.DTO.SQL.SQLRunResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;

@CrossOrigin(allowCredentials = "true", origins = {"http://localhost:4200", "https://racemasters.netlify.app"})
@RestController
@RequestMapping("/api")
public class SQLController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/run-delete-cars-script")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> deleteAllCars() {
        try {
            String currentDir = System.getProperty("user.dir");
//            String sql = Files.readString(Paths.get(currentDir + "\\src\\main\\java\\com\\example\\restapi\\sql_scripts\\delete_books.sql"));
            String sql = Files.readString(Paths.get(currentDir + "/src/main/java/hw4/hw4/SQLScripts/delete_cars.sql"));
            jdbcTemplate.update(sql);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new SQLRunResponseDTO("Successfully deleted all cars"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new SQLRunResponseDTO("Error: something went wrong"));
        }
    }

    @PostMapping("/run-delete-pilots-script")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> deleteAllPilots() {
        try {
            String currentDir = System.getProperty("user.dir");
//            String sql = Files.readString(Paths.get(currentDir + "\\src\\main\\java\\com\\example\\restapi\\sql_scripts\\delete_libraries.sql"));
            String sql = Files.readString(Paths.get(currentDir + "/src/main/java/hw4/hw4/SQLScripts/delete_pilots.sql"));
            jdbcTemplate.update(sql);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new SQLRunResponseDTO("Successfully deleted all pilots"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new SQLRunResponseDTO("Error: something went wrong (make sure you deleted participations first)"));
        }
    }

    @PostMapping("/run-delete-races-script")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> deleteAllRaces() {
        try {
            String currentDir = System.getProperty("user.dir");
//            String sql = Files.readString(Paths.get(currentDir + "\\src\\main\\java\\com\\example\\restapi\\sql_scripts\\delete_memberships.sql"));
            String sql = Files.readString(Paths.get(currentDir + "/src/main/java/hw4/hw4/SQLScripts/delete_races.sql"));
            jdbcTemplate.update(sql);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new SQLRunResponseDTO("Successfully deleted all races"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new SQLRunResponseDTO("Error: something went wrong (make sure you deleted participations first)"));
        }
    }

    @PostMapping("/run-delete-participations-script")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> deleteAllParticipations() {
        try {
            String currentDir = System.getProperty("user.dir");
//            String sql = Files.readString(Paths.get(currentDir + "\\src\\main\\java\\com\\example\\restapi\\sql_scripts\\delete_readers.sql"));
            String sql = Files.readString(Paths.get(currentDir + "/src/main/java/hw4/hw4/SQLScripts/delete_participations.sql"));
            jdbcTemplate.update(sql);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new SQLRunResponseDTO("Successfully deleted all participations"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new SQLRunResponseDTO("Error: something went wrong"));
        }
    }

    @PostMapping("/run-insert-cars-script")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> insertAllCars() {
        try {
            String currentDir = System.getProperty("user.dir");
//            String fullPath = currentDir + "\\src\\main\\java\\com\\example\\restapi\\sql_scripts\\insert_libraries.sql";
            String fullPath = currentDir + "/src/main/java/hw4/hw4/SQLScripts/insert_cars.sql";
            BufferedReader reader = new BufferedReader(new FileReader(fullPath));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                jdbcTemplate.update(line);
            }
            reader.close();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new SQLRunResponseDTO("Successfully inserted all cars"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new SQLRunResponseDTO("Error: something went wrong (make sure you inserted the pilots first)"));
        }
    }

    @PostMapping("/run-insert-pilots-script")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> insertAllPilots() {
        try {
            String currentDir = System.getProperty("user.dir");
//            String fullPath = currentDir + "\\src\\main\\java\\com\\example\\restapi\\sql_scripts\\insert_books.sql";
            String fullPath = currentDir + "/src/main/java/hw4/hw4/SQLScripts/insert_pilots.sql";
            BufferedReader reader = new BufferedReader(new FileReader(fullPath));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                jdbcTemplate.update(line);
            }
            reader.close();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new SQLRunResponseDTO("Successfully inserted all pilots"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new SQLRunResponseDTO("Error: something went wrong"));
        }
    }

    @PostMapping("/run-insert-races-script")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> insertAllRaces() {
        try {
            String currentDir = System.getProperty("user.dir");
//            String fullPath = currentDir + "\\src\\main\\java\\com\\example\\restapi\\sql_scripts\\insert_readers.sql";
            String fullPath = currentDir + "/src/main/java/hw4/hw4/SQLScripts/insert_races.sql";
            BufferedReader reader = new BufferedReader(new FileReader(fullPath));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                jdbcTemplate.update(line);
            }
            reader.close();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new SQLRunResponseDTO("Successfully inserted all races"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new SQLRunResponseDTO("Error: something went wrong"));
        }
    }

    @PostMapping("/run-insert-participations-script")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> insertAllParticipations() {
        try {
            String currentDir = System.getProperty("user.dir");
//            String fullPath = currentDir + "\\src\\main\\java\\com\\example\\restapi\\sql_scripts\\insert_memberships.sql";
            String fullPath = currentDir + "/src/main/java/hw4/hw4/SQLScripts/insert_participations.sql";
            BufferedReader reader = new BufferedReader(new FileReader(fullPath));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                jdbcTemplate.update(line);
            }
            reader.close();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new SQLRunResponseDTO("Successfully inserted all participations"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new SQLRunResponseDTO("Error: something went wrong (make sure you inserted races and pilots first)"));
        }
    }
}