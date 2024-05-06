package br.edu.ifsp.commomleague.user.entities;

import org.springframework.security.core.userdetails.UserDetails;

import br.edu.ifsp.commomleague.app.BaseEntity;
import br.edu.ifsp.commomleague.security.enums.AuthorityName;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
public abstract class UserDetailsAdapter extends BaseEntity implements UserDetails {

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public abstract boolean hasAuthority(AuthorityName name);
    
}
