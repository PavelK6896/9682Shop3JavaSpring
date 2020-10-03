package app.web.pavelk.shop3.config;


import app.web.pavelk.shop3.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//default config
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@Order(100)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private UsersService usersService;

    @Autowired
    public void setUserService(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
//                .antMatchers("/swagger-ui/**", "/swagger-ui.html").permitAll()
                //////////////////////
                //    .antMatchers("/profile/ww").hasAnyAuthority("ROLE_ADMIN")
                //    .antMatchers("/profile/ww/**").hasAnyAuthority("ROLE_ADMIN")
                //    .antMatchers("/profile/ww").hasAnyRole("ADMIN")
                //    .antMatchers("/profile/ww/**").hasAnyRole("ADMIN")
                /////////////////////////////
                .antMatchers("/orders/**", "/profile/**","/paypal/**").hasAnyAuthority("ROLE_USER")
                .antMatchers("/products/add/**", "/profile/admin/**").hasAnyAuthority("ROLE_ADMIN")
                .anyRequest().permitAll()
                .and()
//                .csrf().disable()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/authenticate")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/products")
                .permitAll();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(usersService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
