package br.com.alura.challenge.backend.config;

import br.com.alura.challenge.backend.service.AutenticaTokenFilterService;
import br.com.alura.challenge.backend.service.AutenticacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SpringWebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment env;

    @Autowired
    private AutenticaTokenFilterService autenticaToken;

    @Autowired
    private DataSource dataSource;

    private static final String[] ENDPOINTS_SEM_AUTENTICACAO = {
            // SWAGGER
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            // H2
            "/h2-console",
            "/h2-console/**"
    };

    private static final String[] ENDPOINTS_POST_SEM_AUTENTICACAO = {
            "/autenticacao"
    };

    private static final String[] ENDPOINTS_GET_SEM_AUTENTICACAO = {
            "/videos/free"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (ehAmbienteDeTeste())
            configuracaoDeTeste(http);
         else
            configuracaoDeProducao(http);
    }

    private boolean ehAmbienteDeTeste() {
        return !Arrays.asList(env.getActiveProfiles()).contains("prod");
    }

    private void configuracaoDeProducao(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable()
                    .authorizeRequests()
                    .antMatchers(ENDPOINTS_SEM_AUTENTICACAO).permitAll()
                    .antMatchers(HttpMethod.POST, ENDPOINTS_POST_SEM_AUTENTICACAO).permitAll()
                    .antMatchers(HttpMethod.GET, ENDPOINTS_GET_SEM_AUTENTICACAO).permitAll()
                    .anyRequest().authenticated()
                .and()
                    .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
                .and()
                    .addFilterBefore(autenticaToken, UsernamePasswordAuthenticationFilter.class);
    }

    private void configuracaoDeTeste(HttpSecurity http) throws Exception {
        http.httpBasic().disable().csrf().disable().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable().headers().frameOptions().disable().and()
                .authorizeRequests().anyRequest()
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        UserDetails user = User.builder()
                               .username("admin@alura.com.br")
                               .password(encoder.encode("backend"))
                               .roles("ADM")
                               .build();

        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(encoder)
                .withUser(user);
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new CustomAuthenticationEntryPoint();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
