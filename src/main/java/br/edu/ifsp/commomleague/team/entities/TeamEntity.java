package br.edu.ifsp.commomleague.team.entities;

import java.util.Set;

import br.edu.ifsp.commomleague.app.BaseEntity;
import br.edu.ifsp.commomleague.player.PlayerEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "teams")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TeamEntity extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;
 
    @ManyToMany
    @JoinTable(
        name = "TEAM_PLAYER",
        joinColumns = @JoinColumn(name = "team_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "player_id", referencedColumnName = "id"))
    private Set<PlayerEntity> members;

    @ManyToOne
    @JoinColumn(name = "captain_id", referencedColumnName = "id")
    private PlayerEntity captain;

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id", updatable = false)
    private PlayerEntity createdBy;

    public boolean isMember(PlayerEntity player) {
        return members.stream().anyMatch(member -> member.getId().equals(player.getId()));
    }
    
    public boolean isCaptain(PlayerEntity player) {
        return captain != null && captain.getId().equals(player.getId());
    }

}