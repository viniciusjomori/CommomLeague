package br.edu.ifsp.commomleague.friendship;

import java.util.Collection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifsp.commomleague.friendship.entities.FriendListEntity;
import br.edu.ifsp.commomleague.friendship.services.FriendshipService;
import br.edu.ifsp.commomleague.solicitation.DTOs.SolicitationResponseDTO;
import br.edu.ifsp.commomleague.user.UserMapper;
import br.edu.ifsp.commomleague.user.DTOs.UserInfo;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("friendship")
@PreAuthorize("isAuthenticated()")
public class FriendshipController {
    
    @Autowired
    private FriendshipService friendshipService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/solicitation/user/{id}")
    public ResponseEntity<SolicitationResponseDTO> createSolicitation(@PathVariable UUID id) {
        SolicitationResponseDTO dto = friendshipService.createSolicitation(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/solicitation/{id}/accept")
    public ResponseEntity<Void> accept(@PathVariable UUID id) {
        friendshipService.acceptSolicitation(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("solicitation/{id}/refuse")
    public ResponseEntity<Void> refuse(@PathVariable UUID id) {
        friendshipService.refuseSolicitation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Collection<UserInfo>> findMyFriends() {
        FriendListEntity friendshipEntity = friendshipService.findMyFriends();
        Collection<UserInfo> users = userMapper.toInfo(friendshipEntity.getFriends());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Collection<UserInfo>> findByUser(@PathVariable UUID id) {
        FriendListEntity friendshipEntity = friendshipService.findByUser(id);
        Collection<UserInfo> users = userMapper.toInfo(friendshipEntity.getFriends());
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteFriendship(@PathVariable UUID id) {
        friendshipService.deleteFriendship(id);
        return ResponseEntity.noContent().build();
    }

}
