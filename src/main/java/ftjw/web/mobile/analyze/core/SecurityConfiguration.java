package ftjw.web.mobile.analyze.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

/**
 * 殷晓龙
 * 2020/3/25 16:42
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers("/user/**")
                .hasRole("USER")
                .mvcMatchers("/admin/**")
                .hasRole("ADMIN")
                .and()
                .formLogin()
                .successHandler(new SimpleUrlAuthenticationSuccessHandler("/admin"))
                .and()
                .httpBasic();
    }
    @Autowired
    AgentUserDetailService userDetailService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService)
                .passwordEncoder(passwordEncoder());

/*        auth
                .inMemoryAuthentication()
                .withUser("admin") // 管理员，同时具有 ADMIN,USER权限，可以访问所有资源
                .password("{noop}admin")  //
                .roles("ADMIN", "USER")
                .and()
                .withUser("user").password("{noop}user") // 普通用户，只能访问 /product/**
                .roles("USER");*/
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
