package br.com.faststore.lopestyle.registration.token;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import br.com.faststore.lopestyle.models.Consumer;
import br.com.faststore.lopestyle.models.Employee;
import br.com.faststore.lopestyle.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmationToken {

    @SequenceGenerator(
            name = "confirmation_token_sequence",
            sequenceName = "confirmation_token_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "confirmation_token_sequence"
    )
    private Long id;
    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private LocalDateTime expiratesAt;
    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(
        nullable = false,
        name = "user_id"
    )
    private User user;

    public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiratesAt,
            Consumer user) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiratesAt = expiratesAt;
        this.user = user;
    }
    
    public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiratesAt,
            Employee user) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiratesAt = expiratesAt;
        this.user = user;
    }

}