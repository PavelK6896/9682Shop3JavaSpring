package app.web.pavelk.shop3.config.js;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletResponse;

@Profile("jsBasic")
@Configuration
@Order(90) // преаритет чем меньше тем больше
public class BasicJsSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //переопрнделяю ответ на httpBasic fail login to data base user
        AuthenticationEntryPoint entryPoint = (httpServletRequest, httpServletResponse, e) -> {
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        };

        http.authorizeRequests()
                .antMatchers("/profile/ww/**", "/orders/**").hasAnyRole("ADMIN")
                .antMatchers("/api/v1/products", "/api/v1/products/page").permitAll()
                .antMatchers("/api/v1/**", "/profile/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .httpBasic().authenticationEntryPoint(entryPoint).and()
                .logout()
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
