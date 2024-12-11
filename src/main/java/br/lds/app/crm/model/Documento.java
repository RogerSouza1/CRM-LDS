package br.lds.app.crm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "tb_documento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "categoria", nullable = false)
    private String categoria;

    @Column(name = "numero", nullable = false)
    private String numero;

    @Column(name = "orgao_emissor", nullable = false)
    private String orgaoEmissor;

    @Column(name = "data_emissao", nullable = false)
    private String dataEmissao;

    @Column(name = "data_validade", nullable = false)
    private Date dataValidade;

    @Column(name = "observacao")
    private String observacao;

    @Column(name = "arquivo_url", nullable = false)
    private String arquivoUrl;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}
