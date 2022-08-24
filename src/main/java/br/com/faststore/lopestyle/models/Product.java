package br.com.faststore.lopestyle.models;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.JoinColumn;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String sku;
    private String name;
    private String brand;
    private String description;
    private int inventory;

    @JsonIgnore
    @ManyToMany
    private List<Category> categories;
    
    @JsonIgnore
    @ManyToMany
    private List<Size> sizes;

    @JsonIgnore
    @ManyToMany
    private List<Color> colors;

    @JsonIgnore
    @OneToMany
    private List<Image> images;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar  createdAt;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar updatedAt;

}
