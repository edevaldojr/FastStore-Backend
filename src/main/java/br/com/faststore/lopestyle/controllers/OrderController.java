package br.com.faststore.lopestyle.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.faststore.lopestyle.controllers.dto.FilterDto;
import br.com.faststore.lopestyle.models.Order;
import br.com.faststore.lopestyle.services.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
    
    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public ResponseEntity<Page<Order>> getOrders(@RequestBody FilterDto filterDto) {
        return ResponseEntity.ok().body(orderService.getOrders(filterDto));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable("orderId") int orderId) {
        return ResponseEntity.ok().body(orderService.getOrderById(orderId));
    }

    @PostMapping("/order/{consumerId}")
    public ResponseEntity<Page<Order>> getOrdersConsumer(@PathVariable("consumerId") int consumerId, @RequestBody FilterDto filterDto) {
        return ResponseEntity.ok().body(orderService.getConsumerOrders(consumerId, filterDto));
    }

    @PreAuthorize("hasAnyRole('CONSUMER')")
    @PostMapping("/order")
    public ResponseEntity<Void> insert(@RequestBody Order order) {
        order = orderService.insert(order);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(order.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/order/{orderId}/acceptPayment")
    public ResponseEntity<Void> acceptOrderPayment(@PathVariable("orderId") int orderId) {
        orderService.acceptPaymentOrder(orderId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/order/{orderId}/refusedPayment")
    public ResponseEntity<Void> refusedOrderPayment(@PathVariable("orderId") int orderId) {
        orderService.refusedPaymentOrder(orderId);
        return ResponseEntity.ok().build();
    }

}
