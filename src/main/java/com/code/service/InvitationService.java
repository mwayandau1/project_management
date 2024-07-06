package com.code.service;

import com.code.model.Invitation;
import jakarta.mail.MessagingException;

public interface InvitationService {
    public void sendInvitation(String email, Long projectId) throws MessagingException;

    public Invitation acceptInvitation(String token, Long userId) throws Exception;

    public String getTokenByUserMail(String email);

    public void deleteToken(String token);

}
