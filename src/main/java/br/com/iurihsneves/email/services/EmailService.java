package br.com.iurihsneves.email.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import br.com.iurihsneves.email.enums.StatusEmail;
import br.com.iurihsneves.email.models.EmailModel;
import br.com.iurihsneves.email.repositories.EmailRepository;

@Service
public class EmailService {
    
    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private JavaMailSender emailSender;

    public EmailModel sendEmail(EmailModel emailModel) {

        emailModel.setSendDateEmail(LocalDateTime.now());
        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom(emailModel.getEmailFrom());
            message.setTo(emailModel.getEmailTo());
            message.setSubject(emailModel.getSubject());
            message.setText(emailModel.getText());
            emailSender.send(message);

            emailModel.setStatusEmail(StatusEmail.SENT);

            return emailRepository.save(emailModel);

        } catch (MailException e) {
            emailModel.setStatusEmail(StatusEmail.ERROR);
            return emailRepository.save(emailModel);
        } 

    }

    public Optional<List<EmailModel>> getFailedEmails() {
        Optional<List<EmailModel>> failedEmails = emailRepository.findAllByStatusEmail(StatusEmail.ERROR);

        return failedEmails;
    }

    public Optional<List<EmailModel>> getSuccessfulEmail() {
        Optional<List<EmailModel>> successfulEmails = emailRepository.findAllByStatusEmail(StatusEmail.SENT);

        return successfulEmails;
    }

}
