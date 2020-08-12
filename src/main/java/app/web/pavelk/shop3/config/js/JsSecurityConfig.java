package app.web.pavelk.shop3.config.js;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@Profile("js")
@Order(90) // преаритет чем меньше тем больше
public class JsSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println(" @Profile(\"js\")!!!!!!!!!!!!!!!!!!!!! ");
        http.authorizeRequests()

//                .antMatchers("/profile/ww").permitAll()
                .antMatchers("/profile/ww/**").hasAnyRole("ADMIN")
                .antMatchers("/profile/**").authenticated()
                .antMatchers("/orders/**").hasAnyRole("ADMIN")
                .antMatchers("/api/v1/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .httpBasic()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // не хранит сесию
    }
}
