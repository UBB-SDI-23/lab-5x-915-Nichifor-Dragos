package hw4.hw4.Controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import hw4.hw4.DTO.Register.RegisterDTO;
import hw4.hw4.Entity.User.*;
import hw4.hw4.Exception.JwtTokenInvalidException;
import hw4.hw4.Repository.RoleRepository;
import hw4.hw4.Repository.UserJwtRepository;
import hw4.hw4.Repository.UserProfileRepository;
import hw4.hw4.Repository.UserRepository;
import hw4.hw4.Security.JWT.JwtUtils;
import hw4.hw4.Security.Payload.request.LoginRequest;
import hw4.hw4.Security.Payload.request.SignupRequest;
import hw4.hw4.Security.Payload.response.MessageResponse;
import hw4.hw4.Security.Payload.response.UserInfoResponse;
import hw4.hw4.Security.Services.UserDetailsImpl;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(allowCredentials = "true", origins = {"http://localhost:4200", "https://dynamic-parfait-71d8c1.netlify.app"})
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    AuthenticationManager authenticationManager;

    UserRepository userRepository;

    UserProfileRepository userProfileRepository;

    UserJwtRepository userJwtRepository;

    RoleRepository roleRepository;

    PasswordEncoder encoder;

    JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          UserProfileRepository userProfileRepository,
                          UserJwtRepository userJwtRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder encoder,
                          JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.userJwtRepository = userJwtRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @Transactional
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userJwtRepository.existsByUsername(signUpRequest.getUsername())) {
            userJwtRepository.deleteByUsername(signUpRequest.getUsername());
        }

        String jwtToken = jwtUtils.generateTokenFromUsernameRegister(signUpRequest.getUsername()).getValue();

        UserJwt userJwtPair = new UserJwt();
        userJwtPair.setUsername(signUpRequest.getUsername());
        userJwtPair.setPassword(encoder.encode(signUpRequest.getPassword()));
        userJwtPair.setJwtToken(jwtToken);

        userJwtRepository.save(userJwtPair);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new RegisterDTO(userJwtPair.getUsername(), userJwtPair.getJwtToken()));
    }

    @PostMapping("register/confirm/{jwtToken}")
    public ResponseEntity<?> confirmUser(@PathVariable String jwtToken) {

        if (!userJwtRepository.existsByJwtToken(jwtToken)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Token is invalid!"));
        }

        UserJwt userJwt = userJwtRepository.findByJwtToken(jwtToken);
        if (! jwtUtils.validateJwtToken(userJwt.getJwtToken()))
            throw new JwtTokenInvalidException(userJwt.getJwtToken());

        User user = new User();
        UserProfile userProfile = new UserProfile();
        userProfileRepository.save(userProfile);

        user.setUsername(userJwt.getUsername());
        user.setPassword(userJwt.getPassword());

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: User role not found."));
        roles.add(userRole);
        user.setRoles(roles);
        user.setUserProfile(userProfile);

        userJwtRepository.delete(userJwt);

        userRepository.save(user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponse("Successfully confirmed the registration code!"));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwtCookie = jwtUtils.generateTokenFromUsernameSignIn(userDetails.getUsername()).toString();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, jwtCookie)
                .body(new UserInfoResponse(userDetails.getId(),
                        userDetails.getUsername(),
                        roles));
    }

    @PostMapping("/signout")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> logoutUser() {
        String cookie = jwtUtils.getCleanJwtCookie().toString();
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookie)
                .body(new MessageResponse("You've been signed out!"));
    }
}