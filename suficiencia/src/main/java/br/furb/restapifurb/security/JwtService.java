package br.furb.restapifurb.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private final SecretKey chave; private final long expiracao;
    public JwtService(@Value("${jwt.secret}") String segredo, @Value("${jwt.expiration-ms}") long expiracao) {
        this.chave = Keys.hmacShaKeyFor(segredo.getBytes(StandardCharsets.UTF_8)); this.expiracao = expiracao;
    }
    public String gerarToken(String username) {
        Date agora = new Date();
        return Jwts.builder().subject(username).issuedAt(agora).expiration(new Date(agora.getTime() + expiracao)).signWith(chave).compact();
    }
    public String obterUsername(String token) { return claims(token).getSubject(); }
    public boolean valido(String token, String username) { try { return username.equals(obterUsername(token)) && claims(token).getExpiration().after(new Date()); } catch (JwtException | IllegalArgumentException e) { return false; } }
    private Claims claims(String token) { return Jwts.parser().verifyWith(chave).build().parseSignedClaims(token).getPayload(); }
}
