package br.com.alura.challenge.backend.config.seguranca;

import br.com.alura.challenge.backend.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class TokenAppService {

	@Value("${aplicacao.chave.secreta:chave}")
	private String aplicacaoChaveSecreta;

	@Value("${aplicacao.token.duracao.milis:86400000}")
	private Long tempoDuracaoDoTokenEmMilissegundos;

	public String gerarToken(Authentication authentication) {
		Usuario usuarioLogado = (Usuario) authentication.getPrincipal();

		Date agora = new Date();
		Date dataExpiracao = new Date(agora.getTime() + tempoDuracaoDoTokenEmMilissegundos);

		SecretKey chave = Keys.hmacShaKeyFor(aplicacaoChaveSecreta.getBytes(StandardCharsets.UTF_8));

		return 	Jwts.builder()
					.setIssuer("Alura Challenge - backend")
					.setSubject(usuarioLogado.getId().toString())
					.setIssuedAt(agora)
					.setExpiration(dataExpiracao)
					.signWith(chave)
					.compact();
	}

	public boolean validar(String token) {
		try {
			Jws<Claims> claimsJws = Jwts.parserBuilder()
										.setSigningKey(Keys.hmacShaKeyFor(aplicacaoChaveSecreta.getBytes(StandardCharsets.UTF_8)))
										.build()
										.parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Long capturarIdDoUsuario(String token) {
		Jws<Claims> claimsJws = Jwts.parserBuilder()
									.setSigningKey(Keys.hmacShaKeyFor(aplicacaoChaveSecreta.getBytes(StandardCharsets.UTF_8)))
									.build()
									.parseClaimsJws(token);

		return Long.parseLong(claimsJws.getBody().getSubject());
	}
}
