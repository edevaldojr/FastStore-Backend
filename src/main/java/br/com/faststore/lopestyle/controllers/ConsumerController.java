package br.com.faststore.lopestyle.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/consumer")
public class ConsumerController {
    
    @Autowired
    private ConsumerService consumerService;

    @GetMapping("/{consumerId}")
    public ResponseEntity<Consumer> getConsumer(@PathVariable("consumerId") int consumerId) {
        return ResponseEntity.ok(consumerService.getConsumer(consumerId));
    }
    
    @GetMapping("/all")
    public ResponseEntity<Page<Consumer>> getconsumers(@RequestBody FilterDto consumersFilterDto) {
        Page<Consumer> consumers = consumerService.getConsumersPageable(consumersFilterDto);
        return ResponseEntity.ok(consumers);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Consumer>> searchconsumers(@RequestBody FilterDto consumersFilterDto) {
        List<Consumer> consumers = consumerService.getBySearch(consumersFilterDto);
        return ResponseEntity.ok(consumers);
    }

    @PutMapping("/updateConsumer/{consumerId}")
    public ResponseEntity<Consumer> updateConsumer(@PathVariable("consumerId") int consumerId,@RequestBody Consumer consumer) {
        return ResponseEntity.ok(consumerService.updateConsumer(consumerId, consumer));
    }

    @DeleteMapping("/deleteConsumer/{consumerId}")
    public ResponseEntity<Void> deleteConsumer(@PathVariable("consumerId") int consumerId) {
        consumerService.deleteConsumer(consumerId);
        return ResponseEntity.ok().build();
    }

}
