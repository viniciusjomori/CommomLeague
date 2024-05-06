package br.edu.ifsp.commomleague.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import br.edu.ifsp.commomleague.security.entities.AuthorityEntity;
import br.edu.ifsp.commomleague.security.entities.RoleEntity;
import br.edu.ifsp.commomleague.security.enums.AuthorityName;
import br.edu.ifsp.commomleague.security.enums.RoleName;
import br.edu.ifsp.commomleague.security.repositories.AuthorityRepository;
import br.edu.ifsp.commomleague.security.repositories.RoleRepository;
import br.edu.ifsp.commomleague.user.UserService;
import br.edu.ifsp.commomleague.user.DTOs.UserRegisterDTO;
import br.edu.ifsp.commomleague.user.entities.UserEntity;
import jakarta.transaction.Transactional;

import java.util.Collection;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

@Component
@Transactional
public class SetupRoles implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(alreadySetup) return;

        Arrays.stream(AuthorityName.values()).forEach(this::createAuthority);
        Arrays.stream(RoleName.values()).forEach(this::createRole);

        addAuthority(RoleName.ROLE_FREE_USER, Arrays.asList(
            AuthorityName.SOCIAL_MEDIA, AuthorityName.TOUNAMENT, AuthorityName.TEAM));

        createTestUser("integradoifspprojeto@gmail.com", "teste_free");
    }

    private AuthorityEntity createAuthority(AuthorityName AuthorityName) {
        return authorityRepository.findByAuthorityName(AuthorityName)
            .orElseGet(() -> {
                AuthorityEntity entity = AuthorityEntity.builder()
                    .authorityName(AuthorityName)
                    .roles(new HashSet<>())
                    .build();
                return authorityRepository.save(entity);
            });
    }
    
    private RoleEntity createRole(RoleName roleName) {
        return roleRepository.findByRoleName(roleName)
            .orElseGet(() -> {
                RoleEntity entity = RoleEntity.builder()
                    .roleName(roleName)
                    .authorities(new HashSet<>())
                    .build();
                return roleRepository.save(entity);
            });
    }

    private void addAuthority(RoleName roleName, Collection<AuthorityName> authorityNames) {
        RoleEntity roleEntity = roleRepository.findByRoleName(roleName).get();
        for(AuthorityName an : authorityNames) {
            AuthorityEntity authorityEntity = authorityRepository.findByAuthorityName(an).get();
            if(!roleEntity.getAuthorities().contains(authorityEntity))
                roleEntity.getAuthorities().add(authorityEntity);
        }
        roleRepository.save(roleEntity);
    }

    private UserEntity createTestUser(String email, String nickname) {
        UserRegisterDTO dto = new UserRegisterDTO(
            nickname,
            "Senha@123",
            email,
            "05113585041",
            "11123456789",
            LocalDate.parse("2001-01-01")
        );

        UserEntity user = null;

        try {
            user = userService.registerUser(dto);
        } catch(ResponseStatusException ex) {
            ex.printStackTrace();
        }
        
        return user;
    }

}
