package br.com.faststore.lopestyle.correios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import  org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.faststore.lopestyle.controllers.dto.ShippingResponseDTO;
import br.com.faststore.lopestyle.correios.models.ShippingRequest;
import br.com.faststore.lopestyle.correios.service.ShippingService;

@RestController
@RequestMapping("/consumers/shipping")
public class ShippingController {

    @Autowired
    private ShippingService shippingService;

    @PostMapping("/calcPrecoPrazo")
    public ResponseEntity<ShippingResponseDTO> calcPrecoPrazo(@RequestBody ShippingRequest request) {
        ShippingResponseDTO response = this.shippingService.calcularPrecoPrazo(request);
        return ResponseEntity.ok(response);
    }

}
