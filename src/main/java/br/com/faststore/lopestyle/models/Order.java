package br.com.faststore.lopestyle.models;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
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

import br.com.faststore.lopestyle.models.enums.OrderStatus;
import lombok.Data;

@Data
@Entity
public class Order {
   
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

	@OneToOne
	private Payment payment;

	@OneToMany
	@JoinColumn(name = "orderProduct_id")
    private List<OrderProduct> orderProducts;
	
	@CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createdAt;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar updatedAt;

}
