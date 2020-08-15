package app.web.pavelk.shop3.config.js;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletResponse;

@Configuration
@Profile("js")
@Order(90) // преаритет чем меньше тем больше
public class JsSecurityConfig extends WebSecurityConfigurerAdapter {



    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //переопрнделяю ответ на httpBasic fail login to data base user
        AuthenticationEntryPoint entryPoint = (httpServletRequest, httpServletResponse, e) -> {
            httpServletResponse.setHeader("no no", "Not Form");
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        };

        http.authorizeRequests()


                .antMatchers("/profile/ww/**").hasAnyRole("ADMIN")
                .antMatchers("/profile/**").authenticated()
                .antMatchers("/orders/**").hasAnyRole("ADMIN")
                .antMatchers("/api/v1/products").permitAll()
                .antMatchers("/api/v1/products/page").permitAll()
                .antMatchers("/api/v1/**").authenticated()


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
