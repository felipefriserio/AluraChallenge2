package br.com.alura.challenge.backend.service.validacoes.video;

import br.com.alura.challenge.backend.entity.Categoria;
import br.com.alura.challenge.backend.entity.Video;
import br.com.alura.challenge.backend.service.CategoriaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ValidaCategoriaNoVideo implements ValidacaoVideo {

    public ValidaCategoriaNoVideo(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @Value("${categoria.padrao.id:1}")
    private Long idCategoriaPadrao;

    private final CategoriaService categoriaService;

    @Override
    public void validar(Video video) {
        preencherComCategoriaPadraoSeNecessario(video);
        Long idCategoria = video.getCategoria().getId();
        Categoria categoria = categoriaService.encontrarPorId(idCategoria);
        video.setCategoria(categoria);
    }

    private void preencherComCategoriaPadraoSeNecessario(Video video) {
        Categoria categoria = video.getCategoria();
        if (naoTemCategoria(categoria))
            video.setCategoria(new Categoria(idCategoriaPadrao));
    }

    private boolean naoTemCategoria(Categoria categoria) {
        return categoria == null ||
               categoria.getId() == null ||
               categoria.getId() == 0l;
    }
}
