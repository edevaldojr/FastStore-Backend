package br.com.faststore.lopestyle.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    
    @Id
    private int id;
    private String urlImage;
    
    @ManyToOne
    private Product product;
}
