package hw4.hw4.Security.JWT;

import java.util.Date;

import hw4.hw4.Security.Services.UserDetailsImpl;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import io.jsonwebtoken.*;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${restapi.app.jwtSecret}")
    private String jwtSecret;

    @Value("${restapi.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${restapi.app.jwtCookieName}")
    private String jwtCookie;

    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, this.jwtCookie);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(this.jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(this.jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    public ResponseCookie generateTokenFromUsernameSignin(String username) {
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + this.jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, this.jwtSecret)
                .compact();

        return ResponseCookie.from(this.jwtCookie, token)
                .path("/api")
                .httpOnly(true)
                .build();
    }

    public ResponseCookie generateTokenFromUsernameRegister(String username) {
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + 10 * 60 * 1000)) // 10 minutes available
                .signWith(SignatureAlgorithm.HS512, this.jwtSecret)
                .compact();

        return ResponseCookie.from(this.jwtCookie, token)
                .path("/api/register/confirm")
                .httpOnly(true)
                .build();
    }

    public ResponseCookie getCleanJwtCookie() {
        String token = Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime())) // 0 seconds available
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        return ResponseCookie.from(jwtCookie, token)
                .path("/api")
                .httpOnly(true)
                .build();
    }
}