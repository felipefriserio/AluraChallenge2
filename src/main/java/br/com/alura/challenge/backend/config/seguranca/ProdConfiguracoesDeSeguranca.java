package br.com.alura.challenge.backend.config.seguranca;

import br.com.alura.challenge.backend.config.CustomAuthenticationEntryPoint;
import br.com.alura.challenge.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Profile(value = {"prod", "test"})
public class ProdConfiguracoesDeSeguranca extends WebSecurityConfigurerAdapter {

    @Autowired
    private TokenAppService tokenAppService;

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private static final String[] ENDPOINTS_SEM_AUTENTICACAO = {
            // SWAGGER
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            // H2
            "/h2-console", // remover para prod
            "/h2-console/**" // remover para prod
    };

    private static final String[] ENDPOINTS_POST_SEM_AUTENTICACAO = {
            "/autenticacao"
    };

    private static final String[] ENDPOINTS_GET_SEM_AUTENTICACAO = {
            "/videos/free",
            "/actuator/**"// remover para prod
    };

    // configuracoes de autenticacao
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        auth.userDetailsService(autenticacaoService).passwordEncoder(encoder);
    }

    // configuracoes de autorizacao
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(ENDPOINTS_SEM_AUTENTICACAO).permitAll()
                .antMatchers(HttpMethod.POST, ENDPOINTS_POST_SEM_AUTENTICACAO).permitAll()
                .antMatchers(HttpMethod.GET, ENDPOINTS_GET_SEM_AUTENTICACAO).permitAll()
                .antMatchers(HttpMethod.DELETE, "/videos/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/categorias/*").hasRole("ADMIN")
                .anyRequest().authenticated()
            .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
            .and()
                .addFilterBefore(new AutenticacaoTokenFiltro(tokenAppService, usuarioRepository), UsernamePasswordAuthenticationFilter.class);
    }

    // configuracoes de recursos estaticos - css, js, imagens...
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**");
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new CustomAuthenticationEntryPoint();
    }
}
