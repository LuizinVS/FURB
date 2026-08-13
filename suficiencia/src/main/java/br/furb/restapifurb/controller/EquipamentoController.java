package br.furb.restapifurb.controller;

import br.furb.restapifurb.dto.*;
import br.furb.restapifurb.service.EquipamentoService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/equipamentos")
public class EquipamentoController {
    private final EquipamentoService service;
    public EquipamentoController(EquipamentoService service) { this.service = service; }
    @GetMapping public ResponseEntity<EquipamentosResponseDTO> listar() { return ResponseEntity.ok(new EquipamentosResponseDTO(service.listar())); }
    @GetMapping("/{id}") public ResponseEntity<EquipamentoResponseDTO> buscar(@PathVariable Long id) { return ResponseEntity.ok(service.buscar(id)); }
    @PostMapping public ResponseEntity<EquipamentoResponseDTO> criar(@Valid @RequestBody EquipamentoRequestDTO dto) {
        EquipamentoResponseDTO criado = service.criar(dto);
        return ResponseEntity.created(URI.create("/RestAPIFurb/equipamentos/" + criado.getId())).body(criado);
    }
    @PutMapping("/{id}") public ResponseEntity<EquipamentoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody EquipamentoUpdateDTO dto) { return ResponseEntity.ok(service.atualizar(id, dto)); }
    @DeleteMapping("/{id}") public ResponseEntity<Map<String, Map<String, String>>> remover(@PathVariable Long id) {
        service.remover(id); return ResponseEntity.ok(Map.of("success", Map.of("text", "equipamento removido")));
    }
}
