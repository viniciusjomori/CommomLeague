package br.edu.ifsp.commomleague.solicitation;

import java.time.LocalDateTime;

import br.edu.ifsp.commomleague.app.BaseEntity;
import br.edu.ifsp.commomleague.solicitation.enums.SolicitationStatus;
import br.edu.ifsp.commomleague.user.entities.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class SolicitationEntity extends BaseEntity {
    
    @ManyToOne
    @JoinColumn(name = "user_from_id")
    private UserEntity userFrom;

    @ManyToOne
    @JoinColumn(name = "user_to_id")
    private UserEntity userTo;

    private LocalDateTime responseDate;

    @Enumerated(EnumType.STRING)
    private SolicitationStatus status;

}
