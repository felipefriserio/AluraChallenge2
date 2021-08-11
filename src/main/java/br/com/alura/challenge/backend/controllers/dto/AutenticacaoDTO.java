package br.com.alura.challenge.backend.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AutenticacaoDTO {
	private String tipo;
	private String token;
}
