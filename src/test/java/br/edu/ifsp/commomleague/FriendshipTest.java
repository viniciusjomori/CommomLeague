package br.edu.ifsp.commomleague;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifsp.commomleague.friendship.services.FriendshipService;
import br.edu.ifsp.commomleague.friendship.services.FriendshipSolicitationService;
import br.edu.ifsp.commomleague.solicitation.DTOs.SolicitationResponseDTO;
import br.edu.ifsp.commomleague.solicitation.enums.SolicitationStatus;
import br.edu.ifsp.commomleague.solicitation.exceptions.SolicitationConflictException;
import br.edu.ifsp.commomleague.user.UserService;
import br.edu.ifsp.commomleague.user.DTOs.UserRegisterDTO;
import br.edu.ifsp.commomleague.user.entities.UserEntity;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class FriendshipTest extends BaseTest {

    @Autowired
    UserService userService;

    @Autowired
    FriendshipService friendshipService;

    @Autowired
    FriendshipSolicitationService solicitationService;
    
    UserEntity user1;
    UserEntity user2;
    SolicitationResponseDTO solicitation;

    @BeforeAll
    public void before() {
        user1 = userService.findByNickname("teste_free");

        user2 = userService.registerUser(
            new UserRegisterDTO(
                "teste2",
                "Senha@123",
                "test@gmail.com",
                "532.830.090-40",
                "111111111",
                LocalDate.parse("2001-01-01")
            )
        );
        
        defineCurrentUser(user1);
        
        solicitation = friendshipService.createSolicitation(user2.getId());

        assertEquals(user1.getId(), solicitation.userFrom());
        assertEquals(user2.getId(), solicitation.userTo());
        assertEquals(SolicitationStatus.PENDING, solicitation.status());
    }
    
    @Test
    @Transactional
    public void accept() {
        defineCurrentUser(user2);

        friendshipService.acceptSolicitation(solicitation.id());
    
        assertEquals(true, friendshipService.isFriend(user1, user2));
        assertEquals(SolicitationStatus.ACCEPTED, solicitationService.findById(solicitation.id()).getStatus());
    }
    
    @Test
    @Transactional
    public void refuse() {
        defineCurrentUser(user2);

        friendshipService.refuseSolicitation(solicitation.id());

        assertEquals(false, friendshipService.isFriend(user1, user2));
        assertEquals(SolicitationStatus.REFUSED, solicitationService.findById(solicitation.id()).getStatus());

    }

    @Test
    @Transactional
    public void delete() {
        accept();
        
        defineCurrentUser(user2);

        friendshipService.deleteFriendship(user1.getId());
    
        assertEquals(false, friendshipService.isFriend(user1, user2));
    }

    @Test
    @Transactional
    public void conflict() {
        defineCurrentUser(user2);
        friendshipService.acceptSolicitation(solicitation.id());
        assertThrows(
            SolicitationConflictException.class, () -> {
                friendshipService.acceptSolicitation(solicitation.id());
            }
        );
    }
    
}
