package hw4.hw4.Controller;

import hw4.hw4.DTO.SQL.SQLRunResponseDTO;
import hw4.hw4.Entity.User.ERole;
import hw4.hw4.Entity.User.User;
import hw4.hw4.Exception.UserNotAuthorizedException;
import hw4.hw4.Security.JWT.JwtUtils;
import hw4.hw4.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@CrossOrigin(allowCredentials = "true", origins = {"http://localhost:4200", "https://racemasters.netlify.app"})
@RestController
@RequestMapping("/api")
@Validated
public class SQLController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final JwtUtils jwtUtils;

    private final UserService userService;

    public SQLController(JwtUtils jwtUtils, UserService userService) {
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @PostMapping("/run-delete-cars-script")
    ResponseEntity<?> deleteAllCars(@RequestHeader("Authorization") String token) {
        String username = this.jwtUtils.getUserNameFromJwtToken(token);
        User user = this.userService.getUserByUsername(username);

        boolean isAdmin = user.getRoles().stream().anyMatch((role) ->
                role.getName() == ERole.ROLE_ADMIN
        );

        if (!isAdmin) {
            throw new UserNotAuthorizedException(String.format(user.getUsername()));
        }
        try {
            String currentDir = System.getProperty("user.dir");
//            String sql = Files.readString(Paths.get(currentDir + "\\src\\main\\java\\hw4\\hw4\\SQLScripts\\delete_cars.sql"));
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
    ResponseEntity<?> deleteAllPilots(@RequestHeader("Authorization") String token) {
        String username = this.jwtUtils.getUserNameFromJwtToken(token);
        User user = this.userService.getUserByUsername(username);

        boolean isAdmin = user.getRoles().stream().anyMatch((role) ->
                role.getName() == ERole.ROLE_ADMIN
        );

        if (!isAdmin) {
            throw new UserNotAuthorizedException(String.format(user.getUsername()));
        }
        try {
            String currentDir = System.getProperty("user.dir");
//            String sql = Files.readString(Paths.get(currentDir + "\\src\\main\\java\\hw4\\hw4\\SQLScripts\\delete_pilots.sql"));
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
    ResponseEntity<?> deleteAllRaces(@RequestHeader("Authorization") String token) {
        String username = this.jwtUtils.getUserNameFromJwtToken(token);
        User user = this.userService.getUserByUsername(username);

        boolean isAdmin = user.getRoles().stream().anyMatch((role) ->
                role.getName() == ERole.ROLE_ADMIN
        );

        if (!isAdmin) {
            throw new UserNotAuthorizedException(String.format(user.getUsername()));
        }
        try {
            String currentDir = System.getProperty("user.dir");
//            String sql = Files.readString(Paths.get(currentDir + "\\src\\main\\java\\hw4\\hw4\\SQLScripts\\delete_races.sql"));
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
    ResponseEntity<?> deleteAllParticipations(@RequestHeader("Authorization") String token) {
        String username = this.jwtUtils.getUserNameFromJwtToken(token);
        User user = this.userService.getUserByUsername(username);

        boolean isAdmin = user.getRoles().stream().anyMatch((role) ->
                role.getName() == ERole.ROLE_ADMIN
        );

        if (!isAdmin) {
            throw new UserNotAuthorizedException(String.format(user.getUsername()));
        }
        try {
            String currentDir = System.getProperty("user.dir");
//            String sql = Files.readString(Paths.get(currentDir + "\\src\\main\\java\\hw4\\hw4\\SQLScripts\\delete_participations.sql"));
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
    ResponseEntity<?> insertAllCars(@RequestHeader("Authorization") String token) {
        String username = this.jwtUtils.getUserNameFromJwtToken(token);
        User user = this.userService.getUserByUsername(username);

        boolean isAdmin = user.getRoles().stream().anyMatch((role) ->
                role.getName() == ERole.ROLE_ADMIN
        );

        if (!isAdmin) {
            throw new UserNotAuthorizedException(String.format(user.getUsername()));
        }
        try {
            String currentDir = System.getProperty("user.dir");
//            String fullPath = currentDir + "\\src\\main\\java\\hw4\\hw4\\SQLScripts\\insert_cars.sql";
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
    ResponseEntity<?> insertAllPilots(@RequestHeader("Authorization") String token) {
        String username = this.jwtUtils.getUserNameFromJwtToken(token);
        User user = this.userService.getUserByUsername(username);

        boolean isAdmin = user.getRoles().stream().anyMatch((role) ->
                role.getName() == ERole.ROLE_ADMIN
        );

        if (!isAdmin) {
            throw new UserNotAuthorizedException(String.format(user.getUsername()));
        }
        try {
            String currentDir = System.getProperty("user.dir");
//            String fullPath = currentDir + "\\src\\main\\java\\hw4\\hw4\\SQLScripts\\insert_pilots.sql";
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
    ResponseEntity<?> insertAllRaces(@RequestHeader("Authorization") String token) {
        String username = this.jwtUtils.getUserNameFromJwtToken(token);
        User user = this.userService.getUserByUsername(username);

        boolean isAdmin = user.getRoles().stream().anyMatch((role) ->
                role.getName() == ERole.ROLE_ADMIN
        );

        if (!isAdmin) {
            throw new UserNotAuthorizedException(String.format(user.getUsername()));
        }
        try {
            String currentDir = System.getProperty("user.dir");
//            String fullPath = currentDir + "\\src\\main\\java\\hw4\\hw4\\SQLScripts\\insert_races.sql";
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

    void drop_cars_indexes() throws IOException {
        String currentDir = System.getProperty("user.dir");
        String sql = Files.readString(Paths.get(currentDir + "/src/main/java/hw4/hw4/SQLScripts/cars_indexes/cars_indexes_drop.sql"));
        jdbcTemplate.update(sql);
    }

    void create_cars_indexes() throws IOException {
        String currentDir = System.getProperty("user.dir");
        String sql = Files.readString(Paths.get(currentDir + "/src/main/java/hw4/hw4/SQLScripts/cars_indexes/cars_indexes_create.sql"));
        jdbcTemplate.update(sql);
    }


    @PostMapping("/run-insert-participations-script")
    ResponseEntity<?> insertAllParticipations(@RequestHeader("Authorization") String token) {
        String username = this.jwtUtils.getUserNameFromJwtToken(token);
        User user = this.userService.getUserByUsername(username);

        boolean isAdmin = user.getRoles().stream().anyMatch((role) ->
                role.getName() == ERole.ROLE_ADMIN
        );

        if (!isAdmin) {
            throw new UserNotAuthorizedException(String.format(user.getUsername()));
        }
        try {
            String currentDir = System.getProperty("user.dir");
//            String fullPath = currentDir + "\\src\\main\\java\\hw4\\hw4\\SQLScripts\\insert_participations.sql";
            drop_cars_indexes();
            String fullPath = currentDir + "/src/main/java/hw4/hw4/SQLScripts/insert_participations.sql";
            BufferedReader reader = new BufferedReader(new FileReader(fullPath));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                jdbcTemplate.update(line);
            }
            reader.close();
            create_cars_indexes();
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