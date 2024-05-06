package br.edu.ifsp.commomleague.solicitation;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifsp.commomleague.solicitation.enums.SolicitationStatus;
import br.edu.ifsp.commomleague.solicitation.exceptions.SolicitationBadRequestException;
import br.edu.ifsp.commomleague.solicitation.exceptions.SolicitationConflictException;
import br.edu.ifsp.commomleague.solicitation.exceptions.SolicitationNotFoundException;
import br.edu.ifsp.commomleague.solicitation.exceptions.SolicitationResponseDeniedException;
import br.edu.ifsp.commomleague.user.UserService;
import br.edu.ifsp.commomleague.user.entities.UserEntity;

@Service
public class SolicitationService {
    
    @Autowired
    private SolicitationRepository repository;

    @Autowired
    private UserService userService;


    public SolicitationEntity createSolicitation(SolicitationEntity solicitation) {
        UserEntity currentUser = userService.getCurrentUser();
        
        if(solicitation.getUserTo().equals(currentUser))
            throw new SolicitationBadRequestException();

        solicitation.setUserFrom(currentUser);
        solicitation.setStatus(SolicitationStatus.PENDING);

        return repository.save(solicitation);
    }

    public SolicitationEntity findById(UUID id) {
        return repository.findById(id)
            .orElseThrow(() -> new SolicitationNotFoundException());
    }

    public void accept(SolicitationEntity solicitation) {
        updateStatus(solicitation, SolicitationStatus.ACCEPTED);
    }

    public void refuse(SolicitationEntity solicitation) {
        updateStatus(solicitation, SolicitationStatus.REFUSED);
    }

    private void updateStatus(SolicitationEntity solicitation, SolicitationStatus status) {
        isResponsible(solicitation);
        solicitation.setStatus(status);
        solicitation.setResponseDate(LocalDateTime.now());
        repository.save(solicitation);
    }

    public void isResponsible(SolicitationEntity solicitation) {
        UserEntity currentUser = userService.getCurrentUser();
        if(!solicitation.getUserTo().equals(currentUser))
            throw new SolicitationResponseDeniedException();

        if(!solicitation.getStatus().equals(SolicitationStatus.PENDING))
            throw new SolicitationConflictException();
    }
}
