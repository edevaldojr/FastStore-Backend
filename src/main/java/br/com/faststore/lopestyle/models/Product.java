package br.com.faststore.lopestyle.models;

import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String sku;
    private String name;
    private String brand;
    private String description;
 
    @ManyToOne
    private Category category;

    @OneToMany(cascade=CascadeType.ALL)
    private List<Stock> stock;

    @OneToMany(cascade=CascadeType.ALL)
    private List<Image> images;

    private boolean active;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createdAt;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar updatedAt;


    public void removeImageFromProduct(Image image) {
        this.images.remove(image);
    }

    public void removeStockFromProduct(Stock stock) {
        this.stock.remove(stock);
    }

}
