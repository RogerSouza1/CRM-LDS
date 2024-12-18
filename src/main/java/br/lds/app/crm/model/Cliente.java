package br.lds.app.crm.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.ArrayList;
import java.util.Date;

@Entity
@Table(name = "tb_cliente")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cliente extends Usuario {

    @CNPJ
    @Column(name = "cnpj", nullable = false, updatable = false, unique = true)
    private String cnpj;

    @NotNull
    @Column(name = "razao_social", nullable = false)
    private String razaoSocial;

    @NotNull
    @Column(name = "codigo_empresa", nullable = false)
    private String codigoEmpresa;

    @PastOrPresent
    @Column(name = "data_cadastro", nullable = false)
    private Date dataCadastro;

    @NotNull
    @Column(name = "inscricao_estadual", nullable = false)
    private String inscricaoEstadual;

    @NotNull
    @Column(name = "inscricao_municipal", nullable = false)
    private String inscricaoMunicipal;

    @NotNull
    @Column(name = "numero_ramo_atividade", nullable = false)
    private String numeroRamoAtividade;

    @NotNull
    @Column(name = "nome_ramo_atividade", nullable = false)
    private String nomeRamoAtividade;

    @OneToMany(mappedBy = "cliente")
    private ArrayList<Documento> documentos;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(String codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public String getInscricaoMunicipal() {
        return inscricaoMunicipal;
    }

    public void setInscricaoMunicipal(String inscricaoMunicipal) {
        this.inscricaoMunicipal = inscricaoMunicipal;
    }

    public String getNumeroRamoAtividade() {
        return numeroRamoAtividade;
    }

    public void setNumeroRamoAtividade(String numeroRamoAtividade) {
        this.numeroRamoAtividade = numeroRamoAtividade;
    }

    public String getNomeRamoAtividade() {
        return nomeRamoAtividade;
    }

    public void setNomeRamoAtividade(String nomeRamoAtividade) {
        this.nomeRamoAtividade = nomeRamoAtividade;
    }

    public ArrayList<Documento> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(ArrayList<Documento> documentos) {
        this.documentos = documentos;
    }
}
