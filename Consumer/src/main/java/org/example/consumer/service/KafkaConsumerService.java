package org.example.consumer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumerService {

    @KafkaListener(topics = "topic1", groupId = "group1")
    public void listen(String message) {
        log.info("Received messgae: {}", message);

        processMessage(message);
    }

    private void processMessage(String message) {

    }
}
