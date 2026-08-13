package br.furb.restapifurb.config;

import br.furb.restapifurb.model.*;
import br.furb.restapifurb.repository.*;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {
    @Bean CommandLineRunner carregarDados(TipoRepository tipos, EquipamentoRepository equipamentos, UsuarioRepository usuarios, PasswordEncoder encoder) {
        return args -> {
            if (tipos.count() == 0) {
                Tipo computador = tipos.save(new Tipo("Computador"));
                Tipo audiovisual = tipos.save(new Tipo("audiovisual"));
                tipos.save(new Tipo("Impressora"));
                if (equipamentos.count() == 0) equipamentos.saveAll(List.of(new Equipamento("Notebook Dell", computador), new Equipamento("Projetor Epson", audiovisual), new Equipamento("Notebook Lenovo", computador)));
            }
            if (usuarios.findByUsername("admin").isEmpty()) usuarios.save(new Usuario("admin", encoder.encode("admin123")));
        };
    }
}
