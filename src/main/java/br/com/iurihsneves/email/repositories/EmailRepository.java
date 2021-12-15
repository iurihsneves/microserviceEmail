package br.com.iurihsneves.email.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.iurihsneves.email.models.EmailModel;

public interface EmailRepository extends JpaRepository<EmailModel, Long>{
    
    

}
