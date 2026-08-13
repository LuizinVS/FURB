package br.furb.restapifurb.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import br.furb.restapifurb.dto.*;
import br.furb.restapifurb.model.*;
import br.furb.restapifurb.repository.*;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class EquipamentoServiceTest {
    @Test void atualizacaoParcialPreservaTipo() {
        EquipamentoRepository equipamentos = mock(EquipamentoRepository.class); TipoRepository tipos = mock(TipoRepository.class);
        Tipo tipo = new Tipo("Computador"); tipo.setId(1L); Equipamento existente = new Equipamento("Nome antigo", tipo); existente.setId(1L);
        when(equipamentos.findById(1L)).thenReturn(Optional.of(existente)); when(equipamentos.save(any())).thenAnswer(i -> i.getArgument(0));
        EquipamentoUpdateDTO dto = new EquipamentoUpdateDTO(); dto.setNome("Novo nome");
        EquipamentoResponseDTO resposta = new EquipamentoService(equipamentos, tipos).atualizar(1L, dto);
        assertEquals("Novo nome", resposta.getNome()); assertEquals(1L, resposta.getTipo().getId()); verifyNoInteractions(tipos);
    }
}
