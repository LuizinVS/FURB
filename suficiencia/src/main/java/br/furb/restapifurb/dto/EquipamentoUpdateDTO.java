package br.furb.restapifurb.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

public class EquipamentoUpdateDTO {
    @Size(min = 1, max = 150) private String nome;
    @Valid private TipoDTO tipo;
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public TipoDTO getTipo() { return tipo; }
    public void setTipo(TipoDTO tipo) { this.tipo = tipo; }
}
