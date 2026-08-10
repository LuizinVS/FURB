package br.furb.restapifurb.controller;

import br.furb.restapifurb.dto.EquipamentoDTO;
import br.furb.restapifurb.dto.EquipamentoUpdateDTO;
import br.furb.restapifurb.service.EquipamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/equipamentos")
public class EquipamentoController {

    private final EquipamentoService equipamentoService;

    public EquipamentoController(EquipamentoService equipamentoService) {
        this.equipamentoService = equipamentoService;
    }

    @GetMapping
    @Operation(summary = "Listar equipamentos")
    public ResponseEntity<Map<String, Object>> listar() {
        return ResponseEntity.ok(equipamentoService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar equipamento por ID")
    public ResponseEntity<EquipamentoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(equipamentoService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Criar equipamento", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<EquipamentoDTO> criar(@Valid @RequestBody EquipamentoDTO dto) {
        EquipamentoDTO criado = equipamentoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar equipamento parcialmente", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<EquipamentoDTO> atualizar(@PathVariable Long id, @RequestBody EquipamentoUpdateDTO dto) {
        return ResponseEntity.ok(equipamentoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover equipamento", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Map<String, Object>> remover(@PathVariable Long id) {
        return ResponseEntity.ok(equipamentoService.remover(id));
    }
}
