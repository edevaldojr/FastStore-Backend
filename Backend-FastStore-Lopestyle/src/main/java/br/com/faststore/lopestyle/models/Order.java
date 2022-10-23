package br.com.faststore.lopestyle.models;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.faststore.lopestyle.models.enums.OrderStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(exclude="orderProducts")
public class Order implements Serializable{
   
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private OrderStatus status;

	@ManyToOne
	@JoinColumn(name = "consumer_id")
    private Consumer consumer;

	@ManyToOne
	@JoinColumn(name = "address_id")
	private Address address;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "order")
	private Payment payment;

	@OneToMany(cascade=CascadeType.PERSIST)
	@JoinColumn(name = "id.order")
    private Set<OrderProduct> orderProducts= new HashSet<>();
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	@CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createdAt;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar updatedAt;


	public double getTotalValue() {
        double soma = 0.0;
        for (OrderProduct ip : orderProducts) {
            soma = soma + ip.getSubTotal();
        }
        return soma;
    }

}
