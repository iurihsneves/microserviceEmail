package br.com.iurihsneves.email.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.iurihsneves.email.dto.EmailDto;
import br.com.iurihsneves.email.models.EmailModel;
import br.com.iurihsneves.email.services.EmailService;

@RestController
public class EmailController {
    
    @Autowired
    EmailService emailService;

    @PostMapping("/sending-email")
    public ResponseEntity<EmailModel> sendingEmail(@RequestBody @Valid EmailDto emailDto) {
        
        EmailModel emailModel = new EmailModel();
        BeanUtils.copyProperties(emailDto, emailModel);

        if(emailModel.getSubject().isBlank() || emailModel.getSubject() == null) {
            emailModel.setSubject("Sem assunto.");
        }
        
        emailService.sendEmail(emailModel);

        return new ResponseEntity<>(emailModel, HttpStatus.CREATED);

    }

    @GetMapping("/get-failed-email")
    public ResponseEntity<List<EmailModel>> getFailedEmail() {
        Optional<List<EmailModel>> failedEmailList = emailService.getFailedEmails();

        if(!failedEmailList.get().isEmpty()) {
            return new ResponseEntity<>(failedEmailList.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(failedEmailList.get(), HttpStatus.NO_CONTENT);
        }

    }

    @GetMapping("/get-successful-email")
    public ResponseEntity<List<EmailModel>> getSuccessEmail() {
        Optional<List<EmailModel>> successfulEmailList = emailService.getSuccessfulEmail();

        if(!successfulEmailList.get().isEmpty()) {
            return new ResponseEntity<>(successfulEmailList.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(successfulEmailList.get(), HttpStatus.NO_CONTENT);
        }

    }

}
