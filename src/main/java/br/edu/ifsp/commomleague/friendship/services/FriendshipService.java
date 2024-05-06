package br.edu.ifsp.commomleague.friendship.services;

import java.util.HashSet;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifsp.commomleague.friendship.entities.FriendListEntity;
import br.edu.ifsp.commomleague.friendship.repositories.FriendListRepository;
import br.edu.ifsp.commomleague.solicitation.SolicitationEntity;
import br.edu.ifsp.commomleague.solicitation.DTOs.SolicitationResponseDTO;
import br.edu.ifsp.commomleague.user.UserService;
import br.edu.ifsp.commomleague.user.entities.UserEntity;

@Service
public class FriendshipService {

    @Autowired
    private FriendListRepository repository;

    @Autowired
    private FriendshipSolicitationService solicitationService;

    @Autowired
    private UserService userService;

    public SolicitationResponseDTO createSolicitation(UUID userId) {
        UserEntity user = userService.findById(userId);        
        
        return solicitationService.createSolicitation(user);
    }

    public void acceptSolicitation(UUID id) {
        SolicitationEntity solicitation = solicitationService.accept(id);
        createFriendship(solicitation.getUserFrom(), solicitation.getUserTo());
    }

    public void refuseSolicitation(UUID id) {
        solicitationService.refuse(id);
    }
    
    public void createFriendship(UserEntity user1, UserEntity user2) {
        FriendListEntity f1 = findByUser(user1);
        FriendListEntity f2 = findByUser(user2);
        f1.addFriend(f2);
        f2.addFriend(f1);
        repository.save(f1);
        repository.save(f2);
    }

    public FriendListEntity findByUser(UUID id) {
        UserEntity user = userService.findById(id);
        return findByUser(user);
    }

    public FriendListEntity findByUser(UserEntity user) {
        return repository.findByUser(user)
            .orElseGet(() -> {
                FriendListEntity friendList = FriendListEntity.builder()
                    .user(user)
                    .friends(new HashSet<>())
                    .build();
                return repository.save(friendList);
            }
        );
    }

    public FriendListEntity findMyFriends() {
        UserEntity current = userService.getCurrentUser();
        return findByUser(current);
    }

    public void deleteFriendship(UUID exFriendId) {
        UserEntity currentUser = userService.getCurrentUser();
        UserEntity exFriend = userService.findById(exFriendId);
        FriendListEntity f1 = findByUser(currentUser);
        FriendListEntity f2 = findByUser(exFriend);
        f1.removeFriends(f2);
        f2.removeFriends(f1);
        repository.save(f1);
        repository.save(f2);
    }

    public boolean isFriend(UserEntity user1, UserEntity user2) {
        FriendListEntity f1 = findByUser(user1);
        FriendListEntity f2 = findByUser(user2);
        return f1.isFriend(f2);
    }

}
