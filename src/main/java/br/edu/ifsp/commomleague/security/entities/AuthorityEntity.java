package br.edu.ifsp.commomleague.security.entities;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;

import br.edu.ifsp.commomleague.app.BaseEntity;
import br.edu.ifsp.commomleague.security.enums.AuthorityName;
import br.edu.ifsp.commomleague.user.entities.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "authorities")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityEntity extends BaseEntity implements GrantedAuthority {
    
    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    public AuthorityName authorityName;

    @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
    public Set<RoleEntity> roles;

    public String getAuthority() {
        return this.authorityName.toString();
    }

    public Collection<UserEntity> getUsers() {
        return roles.stream()
            .flatMap(role -> role.getUsers().stream())
            .distinct()
            .collect(Collectors.toList());
    }
}
