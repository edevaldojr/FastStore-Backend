package br.com.faststore.lopestyle.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.faststore.lopestyle.controllers.dto.FilterDto;
import br.com.faststore.lopestyle.models.Consumer;
import br.com.faststore.lopestyle.services.ConsumerService;

@RestController
@RequestMapping("/consumers")
public class ConsumerController {
    
    @Autowired
    private ConsumerService consumerService;

    @PreAuthorize("hasAnyRole('CONSUMER')")
    @GetMapping("/{consumerId}")
    public ResponseEntity<Consumer> getConsumer(@PathVariable("consumerId") int consumerId) {
        return ResponseEntity.ok().body(consumerService.getConsumer(consumerId));
    }
    
    //@PreAuthorize("hasAnyRole('CONSUMER')")
    @PostMapping("/getConsumerByEmail")
    public ResponseEntity<Consumer> getConsumerByEmail(@RequestBody String email) {
        return ResponseEntity.ok().body(consumerService.getConsumerByEmail(email));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<Page<Consumer>> getconsumers(@RequestBody FilterDto consumersFilterDto) {
        Page<Consumer> consumers = consumerService.getConsumersPageable(consumersFilterDto);
        return ResponseEntity.ok().body(consumers);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/search")
    public ResponseEntity<List<Consumer>> searchconsumers(@RequestBody FilterDto consumersFilterDto) {
        List<Consumer> consumers = consumerService.getBySearch(consumersFilterDto);
        return ResponseEntity.ok().body(consumers);
    }
    @PreAuthorize("hasAnyRole('CONSUMER')")
    @PutMapping("/consumer/{consumerId}")
    public ResponseEntity<Consumer> updateConsumer(@PathVariable("consumerId") int consumerId,@RequestBody Consumer consumer) {
        consumerService.updateConsumer(consumerId, consumer);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('CONSUMER')")
    @DeleteMapping("/consumer/{consumerId}")
    public ResponseEntity<Void> deleteConsumer(@PathVariable("consumerId") int consumerId) {
        consumerService.deleteConsumer(consumerId);
        return ResponseEntity.noContent().build();
    }

}
