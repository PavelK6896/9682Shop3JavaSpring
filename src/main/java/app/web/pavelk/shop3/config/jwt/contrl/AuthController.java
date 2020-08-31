package app.web.pavelk.shop3.config.jwt.contrl;


import app.web.pavelk.shop3.config.jwt.dto.JwtRequest;
import app.web.pavelk.shop3.config.jwt.dto.JwtResponse;
import app.web.pavelk.shop3.config.jwt.util1.JwtTokenUtil1;
import app.web.pavelk.shop3.exceptions.JulyMarketError;
import app.web.pavelk.shop3.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Profile("jwt")
@Slf4j
@RestController
public class AuthController {
    private final UsersService usersService;
    private final JwtTokenUtil1 jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(UsersService usersService,
                          JwtTokenUtil1 jwtTokenUtil,
                          AuthenticationManager authenticationManager) {
        this.usersService = usersService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) throws Exception {

        log.info("RestController RestController RestController auth");

        try {
            authenticate(authRequest.getUsername(), authRequest.getPassword());
        } catch (BadCredentialsException ex) {
//            throw new Exception("Incorrect username or password", ex);

            return new ResponseEntity<>(new JulyMarketError(HttpStatus.UNAUTHORIZED.value(),
                    "Incorrect username or password"), HttpStatus.UNAUTHORIZED);

        }

        UserDetails userDetails = usersService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }


}



























