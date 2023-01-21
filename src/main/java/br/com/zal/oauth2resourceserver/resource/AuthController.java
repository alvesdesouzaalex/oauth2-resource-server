package br.com.zal.oauth2resourceserver.resource;

import br.com.zal.oauth2resourceserver.configs.JwtUtils;
import br.com.zal.oauth2resourceserver.dao.UserDao;
import br.com.zal.oauth2resourceserver.dto.AuthRequest;
import br.com.zal.oauth2resourceserver.dto.AuthResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationManager authenticationManager;
    private final UserDao userDao;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserDao userDao, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userDao = userDao;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/token")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            LOGGER.warn("Fail to make a new login with active session. BadRequest");
        }
        final UserDetails user = userDao.findUserByEmail(request.getEmail());
        if (user != null) {
            final String token = jwtUtils.generateToken(user);
            AuthResponse response = AuthResponse.builder()
                    .token(token)
                    .expiration(jwtUtils.extractExpiration(token))
                    .build();
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().build();
    }
}
