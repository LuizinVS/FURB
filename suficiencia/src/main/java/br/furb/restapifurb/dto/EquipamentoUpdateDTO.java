package br.furb.restapifurb.dto;

public class EquipamentoUpdateDTO {
    private String nome;
    private TipoDTO tipo;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoDTO getTipo() {
        return tipo;
    }

    public void setTipo(TipoDTO tipo) {
        this.tipo = tipo;
    }
}
