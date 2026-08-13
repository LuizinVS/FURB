package br.furb.restapifurb.repository;
import br.furb.restapifurb.model.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UsuarioRepository extends JpaRepository<Usuario, Long> { Optional<Usuario> findByUsername(String username); }
