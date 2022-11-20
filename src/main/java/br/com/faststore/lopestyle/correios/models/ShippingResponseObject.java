package br.com.faststore.lopestyle.correios.models;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlRootElement(name = "cServico")
public class ShippingResponseObject {
    
    public int Codigo;
	public String Valor;
	public int PrazoEntrega;
	public String ValorSemAdicionais;
	public String ValorMaoPropria;
	public String ValorAvisoRecebimento;
	public String ValorValorDeclarado;
	public String EntregaDomiciliar;
	public String EntregaSabado;
	public int Erro;
	public String MsgErro;
}