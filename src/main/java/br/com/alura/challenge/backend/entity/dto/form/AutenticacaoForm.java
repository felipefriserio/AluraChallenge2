package br.com.alura.challenge.backend.entity.dto.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
public class AutenticacaoForm {
	@NotEmpty
	private String email;
	@NotEmpty
	private String senha;
}
