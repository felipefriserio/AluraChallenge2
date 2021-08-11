package br.com.alura.challenge.backend.repository;

import br.com.alura.challenge.backend.entity.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private TestEntityManager em;

    @Test
    void deveriaBuscarUsuarioPeloEmail() {
        String nome = "Jose da Silva";
        String email = "admin@alura.com.br";
        String senha = "123456";

        Usuario usuario = new Usuario(nome, email, senha);
        em.persist(usuario);

        Optional<Usuario> usuarioOptional = repository.findByEmail(email);
        assertNotNull(usuarioOptional);
        assertEquals(usuarioOptional.get().getEmail(), email);
    }

    @Test
    void naoDeveriaBuscarUsuarioInexistentePeloEmail() {
        String email = "xpto@alura.com.br";
        Optional<Usuario> usuarioOptional = repository.findByEmail(email);
        assertFalse(usuarioOptional.isPresent());
    }
}
