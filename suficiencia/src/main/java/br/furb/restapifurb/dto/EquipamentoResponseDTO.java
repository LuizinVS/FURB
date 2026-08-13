package br.furb.restapifurb.dto;

public class EquipamentoResponseDTO {
    private Long id; private String nome; private TipoDTO tipo;
    public EquipamentoResponseDTO() {}
    public EquipamentoResponseDTO(Long id, String nome, TipoDTO tipo) { this.id = id; this.nome = nome; this.tipo = tipo; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public TipoDTO getTipo() { return tipo; }
    public void setTipo(TipoDTO tipo) { this.tipo = tipo; }
}
