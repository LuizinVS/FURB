package br.furb.restapifurb.service;

import br.furb.restapifurb.dto.*;
import br.furb.restapifurb.exception.*;
import br.furb.restapifurb.model.*;
import br.furb.restapifurb.repository.*;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EquipamentoService {
    private final EquipamentoRepository equipamentoRepository;
    private final TipoRepository tipoRepository;
    public EquipamentoService(EquipamentoRepository equipamentoRepository, TipoRepository tipoRepository) { this.equipamentoRepository = equipamentoRepository; this.tipoRepository = tipoRepository; }
    @Transactional(readOnly = true)
    public List<EquipamentoResponseDTO> listar() { return equipamentoRepository.findAll().stream().map(this::paraDTO).toList(); }
    @Transactional(readOnly = true)
    public EquipamentoResponseDTO buscar(Long id) { return paraDTO(buscarEntidade(id)); }
    @Transactional
    public EquipamentoResponseDTO criar(EquipamentoRequestDTO dto) {
        Tipo tipo = buscarTipo(dto.getTipo().getId());
        return paraDTO(equipamentoRepository.save(new Equipamento(dto.getNome().trim(), tipo)));
    }
    @Transactional
    public EquipamentoResponseDTO atualizar(Long id, EquipamentoUpdateDTO dto) {
        if (dto.getNome() == null && dto.getTipo() == null) throw new RequisicaoInvalidaException("Informe ao menos um campo para atualizar");
        Equipamento equipamento = buscarEntidade(id);
        if (dto.getNome() != null) {
            if (dto.getNome().isBlank()) throw new RequisicaoInvalidaException("nome não pode estar em branco");
            equipamento.setNome(dto.getNome().trim());
        }
        if (dto.getTipo() != null) equipamento.setTipo(buscarTipo(dto.getTipo().getId()));
        return paraDTO(equipamentoRepository.save(equipamento));
    }
    @Transactional public void remover(Long id) { equipamentoRepository.delete(buscarEntidade(id)); }
    private Equipamento buscarEntidade(Long id) { return equipamentoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Equipamento não encontrado: " + id)); }
    private Tipo buscarTipo(Long id) { if (id == null) throw new RequisicaoInvalidaException("O id do tipo é obrigatório"); return tipoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Tipo não encontrado: " + id)); }
    private EquipamentoResponseDTO paraDTO(Equipamento e) { return new EquipamentoResponseDTO(e.getId(), e.getNome(), new TipoDTO(e.getTipo().getId(), e.getTipo().getNome())); }
}
