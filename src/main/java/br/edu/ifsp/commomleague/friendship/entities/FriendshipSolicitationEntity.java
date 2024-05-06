package br.edu.ifsp.commomleague.friendship.entities;

import br.edu.ifsp.commomleague.solicitation.SolicitationEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "friendship_Solicitation")
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class FriendshipSolicitationEntity extends SolicitationEntity {
    
}