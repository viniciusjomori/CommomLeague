package br.edu.ifsp.commomleague.team;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifsp.commomleague.player.PlayerEntity;
import br.edu.ifsp.commomleague.player.PlayerService;
import br.edu.ifsp.commomleague.solicitation.DTOs.SolicitationResponseDTO;
import br.edu.ifsp.commomleague.team.DTOs.TeamRegisterDTO;
import br.edu.ifsp.commomleague.team.DTOs.TeamResponseDTO;
import br.edu.ifsp.commomleague.team.entities.TeamEntity;
import br.edu.ifsp.commomleague.team.entities.TeamSolicitationEntity;
import br.edu.ifsp.commomleague.team.services.TeamService;
import br.edu.ifsp.commomleague.team.services.TeamSolicitationService;
import br.edu.ifsp.commomleague.user.UserMapper;
import br.edu.ifsp.commomleague.user.DTOs.UserInfo;
import br.edu.ifsp.commomleague.user.entities.UserEntity;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamSolicitationService solicitationService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private UserMapper userMapper;

    @PostMapping
    public ResponseEntity<TeamResponseDTO> registerTeam(@RequestBody TeamRegisterDTO dto) {
        TeamEntity team = teamService.registerTeam(dto);
        return ResponseEntity.ok(teamMapper.toResponseDTO(team));
    }

    @GetMapping
    public ResponseEntity<Collection<TeamResponseDTO>> findAll() {
        Collection<TeamEntity> teams = teamService.findAll();
        return ResponseEntity.ok(teamMapper.toResponseDTO(teams));
    }

    @GetMapping("{id}/members")
    public ResponseEntity<Collection<UserInfo>> findMembers(@PathVariable UUID id) {
        TeamEntity team = teamService.findById(id);
        Collection<UserEntity> users = new ArrayList<>();
        team.getMembers().forEach(p -> {
            users.add(p.getUser());
        });
        return ResponseEntity.ok(userMapper.toInfo(users));
    }

    @PostMapping("{teamId}/invite/user/{userId}")
    public ResponseEntity<SolicitationResponseDTO> invite(@PathVariable UUID teamId, @PathVariable UUID userId) {
        TeamEntity team = teamService.findById(teamId);
        return ResponseEntity.ok(solicitationService.invite(team, userId));
    }

    @PostMapping("/invite/{id}/accept")
    public ResponseEntity<Void> acceptInvite(@PathVariable UUID id) {
        TeamSolicitationEntity solicitation = solicitationService.accept(id);
        PlayerEntity player = playerService.findByUser(solicitation.getUserTo());
        teamService.addMember(solicitation.getTeam(), player);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/invite/{id}/refuse")
    public ResponseEntity<Void> refuseInvite(@PathVariable UUID id) {
        solicitationService.refuse(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{teamId}")
    public ResponseEntity<Void> leaveTeam(@PathVariable UUID teamId) {
        TeamEntity team = teamService.findById(teamId);
        PlayerEntity player = playerService.getCurrentPlayer();
        teamService.leaveTeam(team, player);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{teamId}/user/{userId}")
    public ResponseEntity<Void> kickOut(@PathVariable UUID teamId, @PathVariable UUID userId) {
        TeamEntity team = teamService.findById(teamId);
        PlayerEntity player = playerService.findByUser(userId);
        teamService.leaveTeam(team, player);
        return ResponseEntity.noContent().build();
    }

}
