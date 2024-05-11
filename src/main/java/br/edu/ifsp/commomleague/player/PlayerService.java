package br.edu.ifsp.commomleague.player;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifsp.commomleague.user.UserService;
import br.edu.ifsp.commomleague.user.entities.UserEntity;

@Service
public class PlayerService {
    
    @Autowired
    private PlayerRepository repository;

    @Autowired
    private UserService userService;

    public PlayerEntity findByUser(UserEntity user) {
        return repository.findByUser(user)
            .orElseGet(() -> {
                PlayerEntity player = PlayerEntity.builder()
                    .user(user)
                    .build();
                return repository.save(player);
            }
        );
    }

    public PlayerEntity findByUser(UUID id) {
        UserEntity user = userService.findById(id);
        return findByUser(user);
    }

    public PlayerEntity getCurrentPlayer() {
        UserEntity user = userService.getCurrentUser();
        return findByUser(user);
    }

}
