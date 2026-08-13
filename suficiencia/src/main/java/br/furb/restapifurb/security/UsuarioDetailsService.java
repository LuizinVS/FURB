package br.furb.restapifurb.security;

import br.furb.restapifurb.model.Usuario;
import br.furb.restapifurb.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsService implements UserDetailsService {
    private final UsuarioRepository repository;
    public UsuarioDetailsService(UsuarioRepository repository) { this.repository = repository; }
    @Override public UserDetails loadUserByUsername(String username) {
        Usuario usuario = repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        return User.withUsername(usuario.getUsername()).password(usuario.getPassword()).authorities("USER").build();
    }
}
