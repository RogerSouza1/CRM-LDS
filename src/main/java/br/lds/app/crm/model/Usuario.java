package br.lds.app.crm.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@MappedSuperclass
public abstract class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "Nome é obrigatório")
    @Column(name = "nome", nullable = false)
    private String nome;

    @Email(message = "Email inválido")
    @NotNull(message = "Email é obrigatório")
    @Column(name = "email", nullable = false, unique = true, updatable = false)
    private String email;

    @NotNull(message = "Senha é obrigatória")
    @Column(name = "senha", nullable = false)
    private String senha;

    @NotNull(message = "Telefone é obrigatório")
    @Column(name = "telefone", nullable = false)
    private String telefone;

    @Column(name = "cargo", nullable = false)
    private String cargo;

    @NotNull(message = "Endereço é obrigatório")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id", nullable = false)
    private Endereco endereco;

    @Lob
    private byte[] fotoPerfil;

    @NotNull
    @Column(name = "ativo", nullable = false)
    private boolean isAtivo;

    @NotNull
    @Column(name = "bloqueado", nullable = false)
    private boolean isBloqueado;

    @CreationTimestamp
    private Instant dataCriacao;

    @UpdateTimestamp
    private Instant dataAtualizacao;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + this.getCargo()));
    }

    @Override
    public String getPassword() {
        return this.getSenha();
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.isBloqueado();
    }

    @Override
    public boolean isEnabled() {
        return this.isAtivo();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(byte[] fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public boolean isBloqueado() {
        return this.isBloqueado;
    }

    public void setBloqueado(boolean isBloqueado) {
        this.isBloqueado = isBloqueado;
    }

    public boolean isAtivo() {
        return this.isAtivo;
    }

    public void setAtivo(boolean isAtivo) {
        this.isAtivo = isAtivo;
    }


}