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

    @GetMapping("/getting-failed-email")
    public ResponseEntity<List<EmailModel>> gettingEmail() {
        Optional<List<EmailModel>> failedEmailList = emailService.getFailedEmails();

        if(failedEmailList.isPresent()) {
            return new ResponseEntity<>(failedEmailList.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(failedEmailList.get(), HttpStatus.NO_CONTENT);
        }

    }

}
