package app.web.pavelk.shop3.config.jwt.util1;

import app.web.pavelk.shop3.service.UsersService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Profile("jwt")
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private UsersService usersService;
    private JwtTokenUtil1 jwtTokenUtil1;

    @Autowired
    public void setUsersService(UsersService usersService) {
        this.usersService = usersService;
    }

    @Autowired
    public void setJwtTokenUtil(JwtTokenUtil1 jwtTokenUtil1) {
        this.jwtTokenUtil1 = jwtTokenUtil1;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) { // проверка наличия токена
            jwt = authHeader.substring(7);//орезать строку

            // Здесь происходит валидация токена и будет брошено MalformedJwtException
            try {
                username = jwtTokenUtil1.getUsernameFromToken(jwt);
            } catch (ExpiredJwtException ex) {
                System.out.println("Token is invalid: " + ex.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "{ msg: The token is expired }");
                return;
            }
        }//если токен коректный

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = usersService.loadUserByUsername(username);//детали из токена
            //  if (jwtTokenUtil1.validateToken(jwt, userDetails)) {
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(userDetails,
                            null, userDetails.getAuthorities());

            token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(token);//добовляем юзера в контекст
            //  }
        }


        filterChain.doFilter(request, response);//следующий фильтор
    }
}



























