package br.lds.app.crm.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "tb_cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente extends Usuario {

    @CNPJ
    @Column(name = "cnpj", unique = true)
    private String cnpj;

    @Column(name = "razao_social")
    private String razaoSocial;

    @Column(name = "codigo_empresa")
    private String codigoEmpresa;

    @Column(name = "data_cadastro")
    private Date dataCadastro;

    @Column(name = "inscricao_estadual")
    private String inscricaoEstadual;

    @Column(name = "inscricao_municipal")
    private String inscricaoMunicipal;

    @Column(name = "numero_ramo_atividade")
    private String numeroRamoAtividade;

    @Column(name = "nome_ramo_atividade")
    private String nomeRamoAtividade;

    @OneToMany(mappedBy = "cliente")
    private Set<Documento> documentos;

}
