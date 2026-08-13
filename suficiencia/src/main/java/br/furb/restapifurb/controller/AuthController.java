package br.furb.restapifurb.controller;

import br.furb.restapifurb.dto.*;
import br.furb.restapifurb.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/auth")
public class AuthController {
    private final AuthService service;
    public AuthController(AuthService service) { this.service = service; }
    @PostMapping("/login") public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) { return ResponseEntity.ok(service.login(dto)); }
}
