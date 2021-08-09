package br.com.alura.challenge.backend.config;

import br.com.alura.challenge.backend.entity.Categoria;
import br.com.alura.challenge.backend.entity.Video;
import br.com.alura.challenge.backend.service.CategoriaService;
import br.com.alura.challenge.backend.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class DatabaseStartup {

    public DatabaseStartup(VideoService videoService,
                           CategoriaService categoriaService) {
        this.videoService = videoService;
        this.categoriaService = categoriaService;
    }

    private VideoService videoService;
    private CategoriaService categoriaService;

    @PostConstruct
    private void insereDadosIniciaisNoBanco() {
        String separador = " ----- ";
        log.info(separador + "Inserindo valores no banco de dados" + separador);

        Categoria categoria = new Categoria("LIVRE", "#000000");
        categoriaService.salvar(categoria);
        log.info("salvou categoria {}", categoria);

        Video video = new Video("video","descricao do video", "http://www.site.com.br", categoria);
        videoService.salvar(video);
        log.info("salvou video {}", video);

       /* Usuario usuario = new Usuario("felipefriserio@gmail.com", "admin");
        usuarioService.salvar(usuario);

        Perfil perfil = new Perfil("PADRAO");
        Perfil perfilGestor = new Perfil("ADM");
        perfilRepository.save(perfil);
        perfilRepository.save(perfilGestor);*/
    }
}
