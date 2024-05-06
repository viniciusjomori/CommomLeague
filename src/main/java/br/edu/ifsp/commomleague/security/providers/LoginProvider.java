package br.edu.ifsp.commomleague.security.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import br.edu.ifsp.commomleague.security.exceptions.LoginErrorException;
import br.edu.ifsp.commomleague.security.utils.PasswordUtil;
import br.edu.ifsp.commomleague.user.UserRepository;
import br.edu.ifsp.commomleague.user.entities.UserEntity;


@Component
public class LoginProvider implements AuthenticationProvider{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordUtil passwordUtil;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(
            UsernamePasswordAuthenticationToken.class,
            authentication,
            "Only UsernamePasswordAuthenticationToken is supported"
        );
        
        String email = extractUsername(authentication);
        String password = extractPassword(authentication);

        UserEntity user = userRepository.findByEmail(email)
            .orElseThrow(() -> new LoginErrorException());
        
        if(!verifyPassword(password, user))
            throw new LoginErrorException();

        if(!user.getActive()) {
            throw new LoginErrorException();
        }
            
        return createSuccessAuthetication(user);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private String extractUsername(Authentication authentication) {
        return authentication.getPrincipal().toString();
    }

    private String extractPassword(Authentication authentication) {
        return authentication.getCredentials().toString();
    }

    private boolean verifyPassword(String rawPassword, UserEntity user) {
        return passwordUtil.matches(rawPassword, user);
    }

    private UsernamePasswordAuthenticationToken createSuccessAuthetication(UserEntity user) {
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

}
