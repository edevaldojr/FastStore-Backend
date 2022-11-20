package br.com.faststore.lopestyle.controllers.dto;

import br.com.faststore.lopestyle.correios.models.ShippingResponse;
import lombok.Data;

@Data
public class ShippingResponseDTO {
    
    public int codigo;
	public String valor;
	public int prazoEntrega;
	public int erro;
	public String msgErro;


    public ShippingResponseDTO(ShippingResponse response) {
        this.codigo = response.getCServico().getCodigo();
        this.valor = response.getCServico().getValor();
        this.prazoEntrega = response.getCServico().getPrazoEntrega();
        this.erro = response.getCServico().getErro();
        this.msgErro = response.getCServico().getMsgErro();
    }
}
