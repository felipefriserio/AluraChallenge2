package br.com.alura.challenge.backend.repository.specification;

import org.springframework.data.jpa.domain.Specification;

public interface Especificacao {
    Specification queryAnd();
}
