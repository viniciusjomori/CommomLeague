package br.edu.ifsp.commomleague;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifsp.commomleague.player.PlayerEntity;
import br.edu.ifsp.commomleague.player.PlayerService;
import br.edu.ifsp.commomleague.solicitation.DTOs.SolicitationResponseDTO;
import br.edu.ifsp.commomleague.team.DTOs.TeamRegisterDTO;
import br.edu.ifsp.commomleague.team.entities.TeamEntity;
import br.edu.ifsp.commomleague.team.entities.TeamSolicitationEntity;
import br.edu.ifsp.commomleague.team.services.TeamService;
import br.edu.ifsp.commomleague.team.services.TeamSolicitationService;
import br.edu.ifsp.commomleague.user.UserService;
import br.edu.ifsp.commomleague.user.DTOs.UserRegisterDTO;
import br.edu.ifsp.commomleague.user.entities.UserEntity;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class TeamTest extends BaseTest {
    
    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamSolicitationService solicitationService;

    @Autowired
    private UserService userService;

    @Autowired
    private PlayerService playerService;

    UserEntity user1;
    UserEntity user2;
    TeamEntity team;
    TeamSolicitationEntity solicitation;

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
        TeamRegisterDTO teamRegister = new TeamRegisterDTO("name1");
        team = teamService.registerTeam(teamRegister);

        SolicitationResponseDTO solicitationResponse = solicitationService.invite(team, user2.getId());
        solicitation = solicitationService.findById(solicitationResponse.id());
    }

    @Test
    public void register() {
        defineCurrentUser(user1);

        PlayerEntity current = playerService.getCurrentPlayer();
        
        assertEquals("name1", team.getName());
        assertEquals(1, team.getMembers().size());
        assertEquals(true, team.isMember(current));
        assertEquals(true, team.isCaptain(current));
        assertEquals(true, team.getCreatedBy().getId().equals(current.getId()));

    }

    @Test
    public void invite() {
        assertEquals(user1.getId(), solicitation.getUserFrom().getId());
        assertEquals(user2.getId(), solicitation.getUserTo().getId());
        assertEquals(team.getId(), solicitation.getTeam().getId());

    }

    @Test
    @Transactional
    public void addMember() {
        PlayerEntity player = playerService.findByUser(user2);

        teamService.addMember(team, player);

        team = teamService.findById(team.getId());

        assertEquals(2, team.getMembers().size());
        assertEquals(true, team.isMember(player));

        /* LEAVE TEAM */

        teamService.leaveTeam(team, player);

        assertEquals(1, team.getMembers().size());
        assertEquals(false, team.isMember(player));
    }

}
