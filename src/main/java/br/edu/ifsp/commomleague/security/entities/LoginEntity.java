package br.edu.ifsp.commomleague.security.entities;

import java.time.LocalDateTime;

import br.edu.ifsp.commomleague.app.BaseEntity;
import br.edu.ifsp.commomleague.user.entities.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "logins")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class LoginEntity extends BaseEntity{
    
    private LocalDateTime loginDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}
