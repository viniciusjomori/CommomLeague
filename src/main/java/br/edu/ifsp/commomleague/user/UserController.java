package br.edu.ifsp.commomleague.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifsp.commomleague.user.DTOs.UserRegisterDTO;
import br.edu.ifsp.commomleague.user.DTOs.UserResponseDTO;
import br.edu.ifsp.commomleague.user.entities.UserEntity;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;

@RestController
@RequestMapping("user")
@CrossOrigin(origins = "*")
public class UserController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/current")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponseDTO> getCurrentUser() {
        UserEntity user = userService.getCurrentUser();
        return ResponseEntity.ok(userMapper.toResponseDTO(user));
    }

    @PostMapping
    @PermitAll
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody @Valid UserRegisterDTO dto) {
        UserEntity user = userService.registerUser(dto);
        return ResponseEntity.ok(userMapper.toResponseDTO(user));
    }
}
