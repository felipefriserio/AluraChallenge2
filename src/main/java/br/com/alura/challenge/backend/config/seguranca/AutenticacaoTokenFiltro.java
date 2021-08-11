package br.com.alura.challenge.backend.config.seguranca;

import br.com.alura.challenge.backend.entity.Usuario;
import br.com.alura.challenge.backend.repository.UsuarioRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AutenticacaoTokenFiltro extends OncePerRequestFilter {

	public AutenticacaoTokenFiltro(TokenAppService tokenService, UsuarioRepository usuarioRepository) {
		this.tokenService = tokenService;
		this.usuarioRepository = usuarioRepository;
	}

	private TokenAppService tokenService;
	private UsuarioRepository usuarioRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = capturarToken(request);
		boolean seTokenValido = tokenService.validar(token);
		
		if (seTokenValido)
			autenticarCliente(token);

		filterChain.doFilter(request, response);
	}

	private void autenticarCliente(String token) {
		Long idDoUsuario = tokenService.capturarIdDoUsuario(token);
		Optional<Usuario> usuarioOptional = usuarioRepository.findById(idDoUsuario);
		if (usuarioOptional.isPresent()) {
			Usuario usuario = usuarioOptional.get();
			Authentication autenticacao = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(autenticacao);
		}
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
