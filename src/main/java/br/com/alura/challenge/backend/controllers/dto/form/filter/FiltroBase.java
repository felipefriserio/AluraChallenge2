package br.com.alura.challenge.backend.controllers.dto.form.filter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

@Getter
@Setter
public abstract class FiltroBase {
    private Pageable paginacao;
}