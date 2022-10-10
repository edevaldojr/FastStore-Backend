package br.com.faststore.lopestyle.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.faststore.lopestyle.models.PaymentWithBoleto;

@Service
public class BoletoService {
 
    public void preencherPagamentoComBoleto(PaymentWithBoleto pagto, Calendar instanteDoPedido) {
        instanteDoPedido.add(Calendar.DAY_OF_MONTH, 7);
        pagto.setPaymentDate(instanteDoPedido.getTime());
    }

}
