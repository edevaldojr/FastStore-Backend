package br.com.faststore.lopestyle.models;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import br.com.faststore.lopestyle.models.enums.Perfil;
import lombok.Data;

@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TYPE")
@Data
@Entity
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String email;

    private String password;
    private String firstName;
    private String lastName;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "PERFIS")
    private Set<Integer> perfis = new HashSet<>();

    private Boolean locked = false;
    private Boolean enabled = false;
    private boolean active;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar  createdAt;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar updatedAt;

    public User(int id, String email, String password, String firstName, String lastName, Set<Integer> perfis,
            Calendar createdAt, Calendar updatedAt, boolean active) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.perfis = perfis;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.active = active;
        addPerfil(Perfil.CONSUMER);
    }

    public User() {
        addPerfil(Perfil.CONSUMER);
    }

    public Set<Perfil> getPerfis() {
        return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
    }

    public void addPerfil(Perfil perfil) {
        perfis.add(perfil.getCode());
    }

    public void removePerfil(Perfil perfil) {
        perfis.remove(perfil.getCode());
    }

    public Set<Integer> perfisToInteger(Set<Perfil> perfis) {
        return perfis.stream().map(x -> Perfil.toInt(x)).collect(Collectors.toSet());
    }
    
}
