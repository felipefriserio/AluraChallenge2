package br.com.alura.challenge.backend.service;

import br.com.alura.challenge.backend.entity.Usuario;
import br.com.alura.challenge.backend.exceptions.AutenticacaoException;
import br.com.alura.challenge.backend.repository.UsuarioRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class AutenticaTokenFilterService extends OncePerRequestFilter {

	public AutenticaTokenFilterService(TokenAppService tokenService,
									   UsuarioRepository usuarioRepository) {
		this.tokenService = tokenService;
		this.usuarioRepository = usuarioRepository;
	}

	private TokenAppService tokenService;
	private UsuarioRepository usuarioRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = capturarToken(request);
		boolean seTokenValido = tokenService.validarToken(token);
		
		if (seTokenValido) {
			String username = tokenService.capturarNomeDoUsuario(token);
			Usuario usuario = usuarioRepository.findByUsername(username).get();
			
			UsernamePasswordAuthenticationToken autenticacao = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(autenticacao);
		}

		filterChain.doFilter(request, response);
	}

	private String capturarToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if (headerInvalido(header))
			return "";

		return header.substring(7, header.length());
	}

	private boolean headerInvalido(String header) {
		return header == null || header.isEmpty() || !header.startsWith("Bearer ");
	}
}
