package br.edu.ifsp.commomleague.security.services;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.edu.ifsp.commomleague.security.DTOs.LoginRequestDTO;
import br.edu.ifsp.commomleague.security.DTOs.TokenResponseDTO;
import br.edu.ifsp.commomleague.security.entities.LoginEntity;
import br.edu.ifsp.commomleague.security.entities.TokenEntity;
import br.edu.ifsp.commomleague.security.repositories.LoginRepository;
import br.edu.ifsp.commomleague.security.repositories.TokenRepository;
import br.edu.ifsp.commomleague.security.utils.JwtUtil;
import br.edu.ifsp.commomleague.user.entities.UserEntity;

@Service
public class AuthService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private LoginRepository LoginRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authManager;

    @Value("${security.jwt.expiration}")
    private long jwtExpiration;

    public TokenResponseDTO authenticate(LoginRequestDTO login) {
        Authentication auth = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                login.email(), login.password()
            )
        );
        UserEntity user = (UserEntity) auth.getPrincipal();

        createLoginEntity(user);

        return createTokenResponse(user);
            
    }

    public TokenResponseDTO createTokenResponse(UserEntity user) {
        String token = jwtUtil.generateToken(user);
        saveUserToken(user, token);
        return new TokenResponseDTO(token);
    }

    public void saveUserToken(UserEntity user, String token) {
        revokeAllTokens(user);
        TokenEntity tokenEntity = TokenEntity.builder()
            .user(user)
            .token(token)
            .expirationDate(LocalDateTime.now().plusNanos(
                jwtExpiration * 1000000
            ))
            .build();
        tokenRepository.save(tokenEntity);
    }

    public void revokeAllTokens(UserEntity user) {
        Collection<TokenEntity> tokens = tokenRepository.findActiveByUser(user);
        tokens.forEach(token -> {
            token.setActive(false);
        });
        tokenRepository.saveAll(tokens);

    }

    private void createLoginEntity(UserEntity user) {
        LoginEntity login = LoginEntity.builder()
            .user(user)
            .loginDate(LocalDateTime.now())
            .build();
        LoginRepository.save(login);
    }



}
