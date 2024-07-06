package com.code.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender javaMailSender;


    @Override
    public void sendEmailWithToken(String userEmail, String link) throws MessagingException {

        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, "utf-8");

        String subject = "Invitation To Join Project Team";
        String text = "Please click on the link to join project team "+link;

        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text, true);
        mimeMessageHelper.setTo(userEmail);

        try{
            javaMailSender.send(mimeMailMessage);
        }
        catch (MailSendException e){
            throw new MailSendException("Failed to send email invitation");
        }


    }
}
