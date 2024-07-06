package com.code.service;


import com.code.model.Invitation;
import com.code.repository.InvitationRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvitationServiceImpl implements InvitationService{

    @Autowired
    private InvitationRepository invitationRepository;


    @Autowired
    private EmailService emailService;

    @Override
    public void sendInvitation(String email, Long projectId) throws MessagingException {

        String invitationToken = UUID.randomUUID().toString();

        Invitation invitation = new Invitation();

        invitation.setEmail(email);
        invitation.setProjectId(projectId);
        invitation.setToken(invitationToken);

        invitationRepository.save(invitation);

        String invitationLink = "http://localhost:5000/api/accept_invitation?token="+invitationToken;
        try{
            emailService.sendEmailWithToken(email, invitationLink);
        }
        catch (MailSendException e){
            throw  new MailSendException("Failed to send invitation mail");
        }

    }

    @Override
    public Invitation acceptInvitation(String token, Long userId) throws Exception {

        Invitation invitation = invitationRepository.findByToken(token);
        if(invitation == null){
            throw new Exception("Token invalid or has expired");
        }

        return invitation;
    }

    @Override
    public String getTokenByUserMail(String email) {
        Invitation invitation = invitationRepository.findByEmail(email);

        return invitation.getToken();
    }

    @Override
    public void deleteToken(String token) {
        Invitation invitation = invitationRepository.findByToken(token);

        invitationRepository.delete(invitation);

    }
}
