package br.furb.restapifurb.service;

import br.furb.restapifurb.dto.*;
import br.furb.restapifurb.security.JwtService;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager manager; private final JwtService jwt;
    public AuthService(AuthenticationManager manager, JwtService jwt) { this.manager = manager; this.jwt = jwt; }
    public LoginResponseDTO login(LoginRequestDTO dto) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        return new LoginResponseDTO(jwt.gerarToken(dto.getUsername()));
    }
}
