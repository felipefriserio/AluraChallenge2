package br.com.alura.challenge.backend.service;

import br.com.alura.challenge.backend.entity.Usuario;
import br.com.alura.challenge.backend.entity.dto.AutenticacaoDto;
import br.com.alura.challenge.backend.exceptions.AutenticacaoException;
import br.com.alura.challenge.backend.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@Profile("prod")
public class AutenticacaoService implements UserDetailsService {

    public AutenticacaoService(UsuarioRepository usuarioRepository,
                               AuthenticationManager authManager,
                               TokenAppService tokenAppService) {
        this.usuarioRepository = usuarioRepository;
        this.tokenAppService = tokenAppService;
        this.authManager = authManager;
    }

    private UsuarioRepository usuarioRepository;
    private TokenAppService tokenAppService;
    private AuthenticationManager authManager;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return usuarioRepository.findByUsername(username).get();
        } catch (NoSuchElementException e) {
            throw new UsernameNotFoundException("Nao autorizado");
        }
    }

    public ResponseEntity<AutenticacaoDto> validarCredenciaisEGerarToken(String email, String senha) {
        try {
            autenticar(email, senha);

            Optional<Usuario> usuarioOptional = usuarioRepository.findByUsername(email);
            String token = tokenAppService.gerarToken(usuarioOptional.get());

            return ResponseEntity.ok(new AutenticacaoDto(token, "Bearer"));
        } catch (BadCredentialsException e) {
            log.error("validarCredenciaisEGerarToken - email= {}", email);
            throw new AutenticacaoException("Usuario / senha invalido");
        }
    }

    private void autenticar(String email, String senha) {
        UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(email, senha);
        Authentication authentication = authManager.authenticate(login);
    }
}
