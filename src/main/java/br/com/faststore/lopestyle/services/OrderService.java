package br.com.faststore.lopestyle.services;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.faststore.lopestyle.controllers.dto.FilterDto;
import br.com.faststore.lopestyle.models.Address;
import br.com.faststore.lopestyle.models.Consumer;
import br.com.faststore.lopestyle.models.Order;
import br.com.faststore.lopestyle.models.OrderProduct;
import br.com.faststore.lopestyle.models.PaymentWithBoleto;
import br.com.faststore.lopestyle.models.Stock;
import br.com.faststore.lopestyle.models.enums.OrderStatus;
import br.com.faststore.lopestyle.models.enums.PaymentStatus;
import br.com.faststore.lopestyle.registration.email.EmailService;
import br.com.faststore.lopestyle.repositories.AddressRepository;
import br.com.faststore.lopestyle.repositories.ConsumerRepository;
import br.com.faststore.lopestyle.repositories.OrderProductStockRepository;
import br.com.faststore.lopestyle.repositories.OrderRepository;
import br.com.faststore.lopestyle.repositories.PaymentRepository;
import br.com.faststore.lopestyle.repositories.StockRepository;
import br.com.faststore.lopestyle.security.UserSS;
import br.com.faststore.lopestyle.services.Exceptions.AuthorizationException;
import br.com.faststore.lopestyle.services.Exceptions.InsufficientAmountException;
import br.com.faststore.lopestyle.services.Exceptions.ObjectNotFoundException;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired 
    private ConsumerRepository consumerRepository;

    @Autowired 
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderProductStockRepository orderProductRepository;

    @Autowired 
    private StockRepository stockRepository;

    @Autowired 
    private AddressRepository addressRepository;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private EmailService emailService;

    public Page<Order> getOrders(FilterDto filterDto){
        PageRequest pageable = PageRequest.of(filterDto.getPage(), filterDto.getPageSize());
        return orderRepository.findAll(pageable);
    }

    public Order insert(Order order) {
        Calendar dateNow = Calendar.getInstance();
        Consumer consumer = consumerRepository.findById(order.getConsumer().getId()).orElseThrow(() -> new ObjectNotFoundException(
            "Consumidor não encontrado!, Tipo: " + Consumer.class.getName()));
        
        Address address = addressRepository.save(order.getAddress());

        order.setCreatedAt(dateNow);
        order.setAddress(address);
        order.setConsumer(consumer);
        order.setStatus(OrderStatus.PENDING);
        order.getPayment().setStatus(PaymentStatus.PENDING);
        order.getPayment().setOrder(order);
        if (order.getPayment() instanceof PaymentWithBoleto) {
            PaymentWithBoleto pagto = (PaymentWithBoleto) order.getPayment();
            boletoService.preencherPagamentoComBoleto(pagto, order.getCreatedAt());
        }
        Set<OrderProduct> orderProducts = order.getOrderProducts();
        order.setOrderProducts(new HashSet<>());
        order = orderRepository.save(order);

        paymentRepository.save(order.getPayment());
        for (OrderProduct orderProduct : orderProducts) {
            orderProduct.getId().setOrder(order);
        }

        order.setOrderProducts(orderProducts);

        List<Stock> stocks = stockRepository.findAll();

        for (OrderProduct orderProduct : order.getOrderProducts()) {
            Stock stock = stocks.stream().filter(s-> s.getId() == orderProduct.getStock().getId()).findFirst().orElseThrow(() -> new ObjectNotFoundException(
                "Estoque não encontrado!"));

            if(stock.getQuantity() <= 0 || stock.getQuantity() < orderProduct.getQuantity()){
                throw new InsufficientAmountException("Quantidade do produto insuficiente!");
            } else {
                stock.setQuantity(stock.getQuantity() - orderProduct.getQuantity());
                stockRepository.save(stock);
            }

            orderProduct.setDiscount(0.0);
            orderProduct.setStock(stock);
            orderProduct.setUnityValue(orderProduct.getStock().getUnityValue());
            orderProduct.setOrder(order);
        }
        orderProductRepository.saveAll(order.getOrderProducts());
        emailService.sendOrderConfirmationHtmlEmail(order);
        return order;
    }
    

    public Order getOrderById(int orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ObjectNotFoundException(
            "Pedido não encontrado!, Id: " + orderId + "Tipo: " + Order.class.getName()));

        return order;
    }


    public Page<Order> getConsumerOrders(int consumerId, FilterDto filterDto) {
        UserSS user = UserService.authenticated();
        if (user == null) {
            throw new AuthorizationException("Acesso negado");
        }
        
        PageRequest pageable = PageRequest.of(filterDto.getPage(), filterDto.getPageSize());
        Consumer consumer = consumerRepository.findById(consumerId).orElseThrow(() -> new ObjectNotFoundException(
            "Consumidor não encontrado!, Id: " + consumerId + "Tipo: " + Consumer.class.getName()));;

        Page<Order> orders = orderRepository.findByConsumerId(consumerId, pageable);
        
        return orders;
    }

    public void acceptPaymentOrder(int orderId){
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ObjectNotFoundException(
            "Pedido não encontrado!, Id: " + orderId + "Tipo: " + Order.class.getName()));

        order.getPayment().setStatus(PaymentStatus.PAID);
        order.setStatus(OrderStatus.PREPARING);

        //send email order is preparing to be sent
        orderRepository.save(order);
    }

    public void refusedPaymentOrder(int orderId){
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ObjectNotFoundException(
            "Pedido não encontrado!, Id: " + orderId + "Tipo: " + Order.class.getName()));

        order.getPayment().setStatus(PaymentStatus.REFUSED);
        
        //send email order is preparing to be sent
        orderRepository.save(order);
    }
}
