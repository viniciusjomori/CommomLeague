package br.edu.ifsp.commomleague.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.ifsp.commomleague.security.entities.RoleEntity;
import br.edu.ifsp.commomleague.security.enums.RoleName;
import br.edu.ifsp.commomleague.security.services.RoleService;
import br.edu.ifsp.commomleague.security.utils.PasswordUtil;
import br.edu.ifsp.commomleague.user.DTOs.UserRegisterDTO;
import br.edu.ifsp.commomleague.user.entities.UserEntity;
import br.edu.ifsp.commomleague.user.enums.UserStatus;
import br.edu.ifsp.commomleague.user.exceptions.UserNotFoundException;
import br.edu.ifsp.commomleague.user.exceptions.UserRegisterException;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordUtil passwordUtil;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserMapper userMapper;

    public UserEntity getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserEntity) {
            UUID id = ((UserEntity) principal).getId();
            return userRepository.findById(id).get();
        } else
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user is authenticated");
    }

    public UserEntity findById(UUID id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException());
    }

    public UserEntity findByNickname(String nickname) {
        return userRepository.findByNickname(nickname)
            .orElseThrow(() -> new UserNotFoundException());
    }
    
    public UserEntity registerUser(UserRegisterDTO dto) {
        isValidUser(dto);

        RoleEntity role = roleService.findByRoleName(RoleName.ROLE_FREE_USER);
        String password = passwordUtil.encode(dto.password());
    
        UserEntity entity = userMapper.toEntity(dto);
        
        entity.setRole(role);
        entity.setPassword(password);
        entity.setStatus(UserStatus.ACTIVE);
        
        return userRepository.save(entity);
    }

    public void isValidUser(UserRegisterDTO dto) {
        List<String> errors = new ArrayList<>();

        if(userRepository.existsByCpf(dto.cpf()))
            errors.add("CPF already registred");
        
        if(userRepository.existsByEmail(dto.email()))
            errors.add("Email already registred");
        
        if(userRepository.existsByNickname(dto.nickname()))
            errors.add("Nickname already registred");
        
        if(!passwordUtil.isStrongPassword(dto.password()))
            errors.add("Weak password");
        
        Period period = Period.between(dto.birthday(), LocalDate.now());   
        if(period.getYears() <= 18)
            errors.add("You must be 18 years old or older");
        
        if(!errors.isEmpty())
            throw new UserRegisterException(errors);
    }
}
