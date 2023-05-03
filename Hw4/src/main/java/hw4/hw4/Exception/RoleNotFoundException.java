package hw4.hw4.Exception;


import hw4.hw4.Entity.User.ERole;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(ERole role) {
        super("Could not find role " + role);
    }

}
