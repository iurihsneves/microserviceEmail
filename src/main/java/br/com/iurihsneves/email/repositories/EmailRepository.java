package br.com.iurihsneves.email.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.iurihsneves.email.enums.StatusEmail;
import br.com.iurihsneves.email.models.EmailModel;

public interface EmailRepository extends JpaRepository<EmailModel, Long>{

    Optional<List<EmailModel>> findAllByStatusEmail(StatusEmail statusMail);

}
