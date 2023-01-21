package br.com.zal.oauth2resourceserver.resource;

import br.com.zal.oauth2resourceserver.configs.JwtUtils;
import br.com.zal.oauth2resourceserver.dao.UserDao;
import br.com.zal.oauth2resourceserver.dto.AuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

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
    public ResponseEntity<String> authenticate(@RequestBody AuthRequest request) {
        SecurityContextHolder.getContext().getAuthentication();
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authenticate);
        } catch (BadCredentialsException e) {
            System.out.println(e.getMessage());
        }
        final UserDetails user = userDao.findUserByEmail(request.getEmail());
        if (user != null) {
            SecurityContextHolder.getContext().getAuthentication();
            return ResponseEntity.ok(jwtUtils.generateToken(user));
        }
        return ResponseEntity.badRequest().body("login failed");
    }
}
