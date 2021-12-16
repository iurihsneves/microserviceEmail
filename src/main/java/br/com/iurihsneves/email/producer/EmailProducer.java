package br.com.iurihsneves.email.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import br.com.iurihsneves.email.models.EmailModel;

@Service
public class EmailProducer {

    private static final Logger logger = LoggerFactory.getLogger(EmailProducer.class);
    private final String topic;
    private final KafkaTemplate<String, EmailModel> kafkaTemplate;

    public EmailProducer(@Value("${topic.name}") String topic, KafkaTemplate<String, EmailModel> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(EmailModel email) {
        kafkaTemplate.send(topic, email).addCallback(
            success -> logger.info("Message send" + success.getProducerRecord().value()),
            failure -> logger.info("Message failure" + failure.getMessage())
        );
    }
    
}
