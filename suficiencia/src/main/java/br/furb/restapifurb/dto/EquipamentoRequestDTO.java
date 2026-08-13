package br.furb.restapifurb.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EquipamentoRequestDTO {
    @NotBlank @Size(max = 150) private String nome;
    @Valid @NotNull private TipoDTO tipo;
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public TipoDTO getTipo() { return tipo; }
    public void setTipo(TipoDTO tipo) { this.tipo = tipo; }
}
