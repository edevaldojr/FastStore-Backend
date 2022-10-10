package br.com.faststore.lopestyle.services;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.faststore.lopestyle.models.Consumer;
import br.com.faststore.lopestyle.models.Order;
import br.com.faststore.lopestyle.models.OrderProduct;
import br.com.faststore.lopestyle.models.Payment;
import br.com.faststore.lopestyle.models.PaymentWithBoleto;
import br.com.faststore.lopestyle.models.Stock;
import br.com.faststore.lopestyle.models.enums.PaymentStatus;
import br.com.faststore.lopestyle.registration.email.EmailService;
import br.com.faststore.lopestyle.repositories.ConsumerRepository;
import br.com.faststore.lopestyle.repositories.OrderProductRepository;
import br.com.faststore.lopestyle.repositories.OrderRepository;
import br.com.faststore.lopestyle.repositories.PaymentRepository;
import br.com.faststore.lopestyle.repositories.StockRepository;
import br.com.faststore.lopestyle.services.Exceptions.InsufficientAmountException;
import br.com.faststore.lopestyle.services.Exceptions.ObjectNotFoundException;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired 
    private ConsumerRepository consumerRepository;

    @Autowired 
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired 
    private StockRepository stockRepository;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private EmailService emailService;

    public Order insert(Order order) {
        Calendar dateNow = Calendar.getInstance();
        Consumer consumer = consumerRepository.findById(order.getConsumer().getId()).orElseThrow(() -> new ObjectNotFoundException(
            "Consumidor não encontrado!, Tipo: " + Consumer.class.getName()));

        order.setCreatedAt(dateNow);
        order.setAddress(order.getAddress());
        order.setConsumer(consumer);
        order.getPayment().setStatus(PaymentStatus.PENDING);
        order.getPayment().setOrder(order);
        if (order.getPayment() instanceof PaymentWithBoleto) {
            PaymentWithBoleto pagto = (PaymentWithBoleto) order.getPayment();
            boletoService.preencherPagamentoComBoleto(pagto, order.getCreatedAt());
        }
        order = orderRepository.save(order);
        paymentRepository.save(order.getPayment());

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
    
}
