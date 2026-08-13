package br.furb.restapifurb.dto;

import java.util.List;

public class EquipamentosResponseDTO {
    private List<EquipamentoResponseDTO> equipamentos;
    public EquipamentosResponseDTO(List<EquipamentoResponseDTO> equipamentos) { this.equipamentos = equipamentos; }
    public List<EquipamentoResponseDTO> getEquipamentos() { return equipamentos; }
    public void setEquipamentos(List<EquipamentoResponseDTO> equipamentos) { this.equipamentos = equipamentos; }
}
