package br.com.alura.challenge.backend.service;

import br.com.alura.challenge.backend.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class TokenAppService {

	@Value("${aplicacao.chave.secreta:chave}")
	private String aplicacaoChaveSecreta;

	@Value("${aplicacao.token.duracao.milis:86400000}")
	private Long tempoDuracaoDoTokenEmMilissegundos;

	public String gerarToken(Usuario usuario) {
		Date agora = new Date();
		Date dataExpiracao = new Date(agora.getTime() + tempoDuracaoDoTokenEmMilissegundos);

		String token = 	Jwts.builder()
							.setIssuer("AluraChallenge")
							.setSubject(usuario.getUsername())
							.setIssuedAt(agora)
							.setExpiration(dataExpiracao)
							.signWith(Keys.hmacShaKeyFor(aplicacaoChaveSecreta.getBytes(StandardCharsets.UTF_8)))
							.compact();

		return token;
	}

	@SuppressWarnings("unused")
	public boolean validarToken(String token) {
		try {
			Jws<Claims> claimsJws =
							Jwts.parserBuilder()
								.setSigningKey(Keys.hmacShaKeyFor(aplicacaoChaveSecreta.getBytes(StandardCharsets.UTF_8)))
								.build()
								.parseClaimsJws(token);
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public String capturarNomeDoUsuario(String token) {
		Jws<Claims> claimsJws =
							Jwts.parserBuilder()
								.setSigningKey(Keys.hmacShaKeyFor(aplicacaoChaveSecreta.getBytes(StandardCharsets.UTF_8)))
								.build()
								.parseClaimsJws(token);

		return claimsJws.getBody().getSubject();
	}
}
