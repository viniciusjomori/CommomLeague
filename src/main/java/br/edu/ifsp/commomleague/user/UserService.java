package br.edu.ifsp.commomleague.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.ifsp.commomleague.app.exceptions.BadRequestException;
import br.edu.ifsp.commomleague.security.entities.RoleEntity;
import br.edu.ifsp.commomleague.security.enums.RoleName;
import br.edu.ifsp.commomleague.security.services.RoleService;
import br.edu.ifsp.commomleague.security.utils.PasswordUtil;
import br.edu.ifsp.commomleague.user.DTOs.UserRegisterDTO;
import br.edu.ifsp.commomleague.user.entities.UserEntity;
import br.edu.ifsp.commomleague.user.enums.UserStatus;
import br.edu.ifsp.commomleague.user.exceptions.UserNotFoundException;
import br.edu.ifsp.commomleague.user.strategy.UserValidationStrategy;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Autowired
    private List<UserValidationStrategy> validators;

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
        validateUser(dto);

        RoleEntity role = roleService.findByRoleName(RoleName.ROLE_FREE_USER);
        String password = passwordUtil.encode(dto.password());
    
        UserEntity entity = userMapper.toEntity(dto);
        
        entity.setRole(role);
        entity.setStatus(UserStatus.ACTIVE);        
        entity.setPassword(password);
        return userRepository.save(entity);
    }

    public void validateUser(UserRegisterDTO user) {
        List<String> errors = validators.stream()
            .filter(v -> !v.validate(user))
            .map(UserValidationStrategy::getErrorMessage)
            .collect(Collectors.toList());

        if (!errors.isEmpty()) {
            throw new BadRequestException(errors);
        }
    }

}
