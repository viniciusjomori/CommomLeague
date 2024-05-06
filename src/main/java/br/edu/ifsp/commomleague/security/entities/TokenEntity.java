package br.edu.ifsp.commomleague.security.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

import br.edu.ifsp.commomleague.app.BaseEntity;
import br.edu.ifsp.commomleague.user.entities.UserEntity;

@Entity
@Table(name = "tokens")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class TokenEntity extends BaseEntity {
    
    @Column(unique = true, nullable = false)
    private String token;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    
    @Column(nullable = false)
    private LocalDateTime expirationDate;

    public boolean isNonExpired() {
        return !LocalDateTime.now().isAfter(expirationDate);
    }

    @Override
    public Boolean getActive() {
        return super.getActive() && isNonExpired(); 
    }
}
