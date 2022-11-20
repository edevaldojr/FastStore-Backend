package br.com.faststore.lopestyle.correios.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShippingRequest {
    
    private String nCdEmpresa;
    private String sDsSenha;
    @JsonProperty("n_cd_servico")
    private String nCdServico;
    private String sCepOrigem;
    @JsonProperty("s_cep_destino")
    private String sCepDestino;
    @JsonProperty("n_vl_peso")
    private String nVlPeso;
    @JsonProperty("n_cd_formato")
    private int nCdFormato;
    @JsonProperty("n_vl_comprimento")
    private Double nVlComprimento;
    @JsonProperty("n_vl_altura")
    private Double nVlAltura;
    @JsonProperty("n_vl_largura")
    private Double nVlLargura;
    private Double nVlDiametro;
    private String sCdMaoPropria;
    private Double nVlValorDeclarado;
    private String sCdAvisoRecebimento;

}
