package br.furb.restapifurb.service;

import br.furb.restapifurb.dto.EquipamentoDTO;
import br.furb.restapifurb.dto.EquipamentoUpdateDTO;
import br.furb.restapifurb.dto.TipoDTO;
import br.furb.restapifurb.exception.ResourceNotFoundException;
import br.furb.restapifurb.model.Equipamento;
import br.furb.restapifurb.model.Tipo;
import br.furb.restapifurb.repository.EquipamentoRepository;
import br.furb.restapifurb.repository.TipoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class EquipamentoService {

    private final EquipamentoRepository equipamentoRepository;
    private final TipoRepository tipoRepository;

    public EquipamentoService(EquipamentoRepository equipamentoRepository, TipoRepository tipoRepository) {
        this.equipamentoRepository = equipamentoRepository;
        this.tipoRepository = tipoRepository;
    }

    public Map<String, Object> listarTodos() {
        List<Equipamento> equipamentos = equipamentoRepository.findAll();
        List<EquipamentoDTO> itens = equipamentos.stream().map(this::toDto).toList();
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("equipamentos", itens);
        return response;
    }

    public EquipamentoDTO buscarPorId(Long id) {
        Equipamento equipamento = equipamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipamento não encontrado"));
        return toDto(equipamento);
    }

    @Transactional
    public EquipamentoDTO criar(EquipamentoDTO dto) {
        Tipo tipo = buscarTipo(dto.getTipo().getId());
        Equipamento equipamento = new Equipamento();
        equipamento.setNome(dto.getNome());
        equipamento.setTipo(tipo);
        return toDto(equipamentoRepository.save(equipamento));
    }

    @Transactional
    public EquipamentoDTO atualizar(Long id, EquipamentoUpdateDTO dto) {
        Equipamento equipamento = equipamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipamento não encontrado"));

        if (dto.getNome() != null) {
            equipamento.setNome(dto.getNome());
        }

        if (dto.getTipo() != null) {
            Tipo tipo = buscarTipo(dto.getTipo().getId());
            equipamento.setTipo(tipo);
        }

        return toDto(equipamentoRepository.save(equipamento));
    }

    @Transactional
    public Map<String, Object> remover(Long id) {
        Equipamento equipamento = equipamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipamento não encontrado"));
        equipamentoRepository.delete(equipamento);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("success", Map.of("text", "equipamento removido"));
        return response;
    }

    private Tipo buscarTipo(Long id) {
        return tipoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo não encontrado"));
    }

    private EquipamentoDTO toDto(Equipamento equipamento) {
        EquipamentoDTO dto = new EquipamentoDTO();
        dto.setId(equipamento.getId());
        dto.setNome(equipamento.getNome());
        TipoDTO tipoDto = new TipoDTO();
        tipoDto.setId(equipamento.getTipo().getId());
        tipoDto.setNome(equipamento.getTipo().getNome());
        dto.setTipo(tipoDto);
        return dto;
    }
}
