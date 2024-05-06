package br.edu.ifsp.commomleague.security.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Collection;
import java.util.Set;

import br.edu.ifsp.commomleague.app.BaseEntity;
import br.edu.ifsp.commomleague.security.enums.AuthorityName;
import br.edu.ifsp.commomleague.security.enums.RoleName;
import br.edu.ifsp.commomleague.user.entities.UserEntity;

@Entity
@Table(name = "roles")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity extends BaseEntity{
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RoleName roleName;
    
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private Set<UserEntity> users;

    @ManyToMany(
        cascade = { CascadeType.PERSIST, CascadeType.MERGE }, 
        fetch = FetchType.LAZY)
    @JoinTable(name = "ROLE_AUTHORITY",
        joinColumns = @JoinColumn(name = "role_id"),
        inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Collection<AuthorityEntity> authorities;
    
    public boolean hasAuthority(AuthorityName authorityName) {
        return this.authorities
            .stream()
            .anyMatch(a -> a.getAuthorityName().equals(authorityName));
    }
}
