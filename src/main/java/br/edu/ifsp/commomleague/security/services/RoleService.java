package br.edu.ifsp.commomleague.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.ifsp.commomleague.security.entities.RoleEntity;
import br.edu.ifsp.commomleague.security.enums.RoleName;
import br.edu.ifsp.commomleague.security.repositories.RoleRepository;

import java.util.UUID;

@Service
public class RoleService {
    
    @Autowired
    private RoleRepository roleRepository;

    public RoleEntity findById(UUID id) {
        return roleRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));
    }

    public RoleEntity findByRoleName(RoleName roleName) {
        return roleRepository.findByRoleName(roleName)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));
    }

}