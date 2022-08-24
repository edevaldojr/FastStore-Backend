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

import br.com.faststore.lopestyle.models.utils.OrderStatus;
import lombok.Data;

@Data
@Entity
public class Order {
   
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private OrderStatus status;

	@ManyToOne
	@JoinColumn(name = "consumerId")
    private Consumer consumer;

	@ManyToOne
	@JoinColumn(name = "addressId")
	private Address address;

	@OneToOne
	private Payment payment;

	@OneToMany
	@JoinColumn(name = "orderProductId")
    private List<OrderProduct> orderProduct;
	
	@CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createdAt;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar updatedAt;

}
