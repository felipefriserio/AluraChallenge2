package br.com.alura.challenge.backend.controllers.dto.form;

import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotEmpty;

@Data
public class AutenticacaoForm {
	@NotEmpty
	private String email;
	@NotEmpty
	private String senha;

	public UsernamePasswordAuthenticationToken converter(){
		return new UsernamePasswordAuthenticationToken(email, senha);
	}
}
