package br.furb.restapifurb.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity @Table(name = "usuarios")
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank @Column(nullable = false, unique = true, length = 100)
    private String username;
    @NotBlank @Column(nullable = false)
    private String password;
    public Usuario() {}
    public Usuario(String username, String password) { this.username = username; this.password = password; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
