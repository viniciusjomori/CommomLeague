package br.edu.ifsp.commomleague;

import java.util.ArrayList;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifsp.commomleague.user.entities.UserEntity;

public class BaseTest {
    
    @Transactional
    public void defineCurrentUser(UserEntity user) {
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(
                user,
                null,
                new ArrayList<>()
            )
        );
    }

}
