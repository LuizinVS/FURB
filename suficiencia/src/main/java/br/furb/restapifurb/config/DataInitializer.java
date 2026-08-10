package br.furb.restapifurb.config;

import br.furb.restapifurb.model.Equipamento;
import br.furb.restapifurb.model.Tipo;
import br.furb.restapifurb.model.Usuario;
import br.furb.restapifurb.repository.EquipamentoRepository;
import br.furb.restapifurb.repository.TipoRepository;
import br.furb.restapifurb.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final TipoRepository tipoRepository;
    private final EquipamentoRepository equipamentoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(TipoRepository tipoRepository, EquipamentoRepository equipamentoRepository,
                          UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.tipoRepository = tipoRepository;
        this.equipamentoRepository = equipamentoRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (tipoRepository.count() == 0) {
            Tipo computador = new Tipo();
            computador.setNome("Computador");
            tipoRepository.save(computador);

            Tipo audiovisual = new Tipo();
            audiovisual.setNome("audiovisual");
            tipoRepository.save(audiovisual);

            Tipo impressora = new Tipo();
            impressora.setNome("Impressora");
            tipoRepository.save(impressora);
        }

        if (equipamentoRepository.count() == 0) {
            Tipo computador = tipoRepository.findAll().stream().filter(t -> t.getNome().equals("Computador")).findFirst().orElseThrow();
            Tipo audiovisual = tipoRepository.findAll().stream().filter(t -> t.getNome().equals("audiovisual")).findFirst().orElseThrow();

            Equipamento notebookDell = new Equipamento();
            notebookDell.setNome("Notebook Dell");
            notebookDell.setTipo(computador);
            equipamentoRepository.save(notebookDell);

            Equipamento projetorEpson = new Equipamento();
            projetorEpson.setNome("Projetor Epson");
            projetorEpson.setTipo(audiovisual);
            equipamentoRepository.save(projetorEpson);

            Equipamento notebookLenovo = new Equipamento();
            notebookLenovo.setNome("Notebook Lenovo");
            notebookLenovo.setTipo(computador);
            equipamentoRepository.save(notebookLenovo);
        }

        if (usuarioRepository.findByUsername("admin").isEmpty()) {
            Usuario usuario = new Usuario();
            usuario.setUsername("admin");
            usuario.setPassword(passwordEncoder.encode("admin123"));
            usuarioRepository.save(usuario);
        }
    }
}
