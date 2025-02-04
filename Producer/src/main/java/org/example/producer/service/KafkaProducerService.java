package org.example.producer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.producer.model.IAGProvisioningReqeuestJSON;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {
    private final KafkaTemplate<String, IAGProvisioningReqeuestJSON> kafkaTemplate;

    public void sendMessage(String topicName, IAGProvisioningReqeuestJSON message) {
        log.info("send message to topic {}", topicName);
        log.info("message {}", message);
        kafkaTemplate.send(topicName, message);
    }
}
