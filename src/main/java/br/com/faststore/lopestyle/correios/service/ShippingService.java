package br.com.faststore.lopestyle.correios.service;

import java.io.StringReader;
import java.time.Duration;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.http.HttpEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.faststore.lopestyle.controllers.dto.ShippingResponseDTO;
import br.com.faststore.lopestyle.correios.exception.handler.RestTemplateResponseErrorHandler;
import br.com.faststore.lopestyle.correios.models.ShippingRequest;
import br.com.faststore.lopestyle.correios.models.ShippingResponse;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ShippingService {
 
    @Value("${shipping.cep.origin}")
    private String cepOrigem;
    
    private final RestTemplate restTemplate;
    private final String POST_URL = "http://ws.correios.com.br/calculador/CalcPrecoPrazo.aspx?";
    
    public ShippingService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(10)).errorHandler(new RestTemplateResponseErrorHandler()).build();
    }

    public ShippingResponseDTO calcularPrecoPrazo(ShippingRequest request){
        String url = this.populateUrl(request);
        String responseXml = this.postRequest(request, url);
        ShippingResponse response = this.stringXmlToObject(responseXml);
        ShippingResponseDTO responseDTO = new ShippingResponseDTO(response);
        return responseDTO;
    }

    public String postRequest(ShippingRequest request, String url) {

        HttpEntity<ShippingRequest> entity = new HttpEntity<ShippingRequest>(request);
        String responseXml = "";
        try{
            responseXml = restTemplate.postForObject(url, entity, String.class);
        }catch(Exception exception){
            log.info("Erro ao calcular preço e prazo: " + exception);
        }
        
        log.info(responseXml);
        return responseXml;
    }

    public String populateUrl(ShippingRequest request){
        String url = POST_URL;
        url += "&nCdEmpresa=";
        url += "&sDsSenha=";
        url += "&nCdServico=" + request.getNCdServico();
        url += "&sCepOrigem=" + this.cepOrigem;
        url += "&sCepDestino=" + request.getSCepDestino();
        url += "&nVlPeso=" + request.getNVlPeso();
        url += "&nVlComprimento=" + request.getNVlComprimento();
        url += "&nVlAltura=" + request.getNVlAltura();
        url += "&nVlLargura=" + request.getNVlLargura();
        url += "&nVlDiametro=0";
        url += "&sCdMaoPropria=n";
        url += "&nVlValorDeclarado=0";
        url += "&sCdAvisoRecebimento=n";
        url += "&StrRetorno=xml";
        url += "&nIndicaCalculo=3";
        log.info(url);
        return url;
    }

    public ShippingResponse stringXmlToObject(String responseXml){
        ShippingResponse response = new ShippingResponse();
        try {
            StringReader sr = new StringReader(responseXml);
            JAXBContext jaxbContext = JAXBContext.newInstance(ShippingResponse.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            response = (ShippingResponse) unmarshaller.unmarshal(sr);
        } catch (JAXBException exception) {
            log.info(exception);
        }
        return response;
    }
}
