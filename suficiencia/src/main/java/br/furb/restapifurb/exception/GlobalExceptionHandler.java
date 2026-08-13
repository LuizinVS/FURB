package br.furb.restapifurb.exception;

import java.time.Instant;
import java.util.*;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RecursoNaoEncontradoException.class) ResponseEntity<Map<String,Object>> naoEncontrado(RecursoNaoEncontradoException e) { return erro(HttpStatus.NOT_FOUND, e.getMessage(), null); }
    @ExceptionHandler(RequisicaoInvalidaException.class) ResponseEntity<Map<String,Object>> invalida(RequisicaoInvalidaException e) { return erro(HttpStatus.BAD_REQUEST, e.getMessage(), null); }
    @ExceptionHandler(BadCredentialsException.class) ResponseEntity<Map<String,Object>> credenciais() { return erro(HttpStatus.UNAUTHORIZED, "Usuário ou senha inválidos", null); }
    @ExceptionHandler(HttpMessageNotReadableException.class) ResponseEntity<Map<String,Object>> jsonInvalido() { return erro(HttpStatus.BAD_REQUEST, "JSON malformado ou com valor incompatível", null); }
    @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<Map<String,Object>> validacao(MethodArgumentNotValidException e) {
        Map<String,String> campos = new LinkedHashMap<>();
        e.getBindingResult().getFieldErrors().forEach(f -> campos.putIfAbsent(f.getField(), f.getDefaultMessage()));
        return erro(HttpStatus.BAD_REQUEST, "Dados inválidos", campos);
    }
    private ResponseEntity<Map<String,Object>> erro(HttpStatus status, String mensagem, Map<String,String> campos) {
        Map<String,Object> body = new LinkedHashMap<>(); body.put("timestamp", Instant.now()); body.put("status", status.value()); body.put("erro", mensagem); if (campos != null) body.put("campos", campos);
        return ResponseEntity.status(status).body(body);
    }
}
