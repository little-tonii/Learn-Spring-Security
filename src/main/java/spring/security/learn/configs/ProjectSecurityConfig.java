package spring.security.learn.configs;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
                httpSecurity.authorizeHttpRequests(
                                request -> request
                                                .requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards")
                                                .authenticated()
                                                .requestMatchers("/notices", "/contact")
                                                .permitAll())
                                .formLogin(Customizer.withDefaults())
                                .httpBasic(Customizer.withDefaults());
                return httpSecurity.build();
        }

        // @Bean
        // UserDetailsManager userDetailsManager() {
        // UserDetails admin = User.builder()
        // .username("admin")
        // .password("12345")
        // .authorities("admin")
        // .build();
        // UserDetails user = User.builder()
        // .username("user")
        // .password("12345")
        // .authorities("read")
        // .build();
        // return new InMemoryUserDetailsManager(admin, user);
        // }

        @Bean
        UserDetailsService userDetailsService(DataSource dataSource) {
                return new JdbcUserDetailsManager(dataSource);
        }

        @Bean
        PasswordEncoder passwordEncoder() {
                return NoOpPasswordEncoder.getInstance();
        }
}
