package br.com.alura.challenge.backend.service.validacoes.video.incluir;

import br.com.alura.challenge.backend.entity.Categoria;
import br.com.alura.challenge.backend.entity.Video;
import br.com.alura.challenge.backend.service.CategoriaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ValidaCategoria implements ValidacaoParaIncluirVideo {

    public ValidaCategoria(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @Value("${categoria.padrao.id:1}")
    private Long idCategoriaPadrao;

    private final CategoriaService categoriaService;

    @Override
    public void validar(Video video) {
        atualizarParaCategoriaPadraoSeNecessario(video);
    }

    private void atualizarParaCategoriaPadraoSeNecessario(Video video) {
        if (seVideoNaoTemCategoria(video)){
            Categoria categoriaPadrao = categoriaService.encontrarPorId(idCategoriaPadrao);
            video.setCategoria(categoriaPadrao);
        }
    }

    private boolean seVideoNaoTemCategoria(Video video) {
        Categoria categoria = video.getCategoria();

        if (categoria == null )
            return true;
        else
            return categoria.getId() == null ||
                   categoria.getId() == 0l;
    }
}
