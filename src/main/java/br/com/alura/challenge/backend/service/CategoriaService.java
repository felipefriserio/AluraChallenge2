package br.com.alura.challenge.backend.service;

import br.com.alura.challenge.backend.entity.Categoria;
import br.com.alura.challenge.backend.controllers.dto.form.filter.CategoriaFiltro;
import br.com.alura.challenge.backend.exceptions.EntidadeNaoEncontradaException;
import br.com.alura.challenge.backend.repository.CategoriaRepository;
import br.com.alura.challenge.backend.repository.specification.CategoriaEspecificacao;
import br.com.alura.challenge.backend.repository.specification.Especificacao;
import br.com.alura.challenge.backend.service.validacoes.categoria.ValidacaoCategoria;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    public CategoriaService(CategoriaRepository repository,
                            List<ValidacaoCategoria> listaDeValidacoes) {
        this.repository = repository;
        this.listaDeValidacoes = listaDeValidacoes;
    }

    private CategoriaRepository repository;
    private List<ValidacaoCategoria> listaDeValidacoes;

    public Page<Categoria> listar(CategoriaFiltro filtro) {
        Especificacao categoriaEspecificacao = new CategoriaEspecificacao(filtro);
        Specification queryAnd = categoriaEspecificacao.queryAnd();

        Pageable paginacao = filtro.getPaginacao();
        return repository.findAll(queryAnd, paginacao);
    }

    public Categoria encontrarPorId(Long id) {
        if (id == null) throw new IllegalArgumentException("id nulo");

        Optional<Categoria> categoriaOptional = repository.findById(id);
        if (categoriaOptional.isPresent())
            return categoriaOptional.get();
        else
            throw new EntidadeNaoEncontradaException("CategoriaId " + id + " nao encontrado");
    }

    @Transactional
    public Categoria salvar(Categoria categoria) {
        for (ValidacaoCategoria validacao : listaDeValidacoes) {
            validacao.validar(categoria);
        }
        return repository.save(categoria);
    }

    public Categoria atualizar(Categoria categoriaAtualizada) {
        for (ValidacaoCategoria validacao : listaDeValidacoes) {
            validacao.validar(categoriaAtualizada);
        }

        Long id = categoriaAtualizada.getId();

        Categoria categoria = encontrarPorId(id);
        categoria.setTitulo(categoriaAtualizada.getTitulo());
        categoria.setCor(categoriaAtualizada.getCor());

        return categoria;
    }

    public void deletar(Long id) {
        Categoria categoria = encontrarPorId(id);
        deletar(categoria);
    }

    @Transactional
    public void deletar(Categoria categoria) {
        repository.delete(categoria);
    }

    @PostConstruct
    private void ordenarListaDeValidacoesParaExecucao() {
        Collections.sort(listaDeValidacoes, AnnotationAwareOrderComparator.INSTANCE);
     }
}
