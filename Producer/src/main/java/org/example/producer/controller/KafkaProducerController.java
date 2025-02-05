package org.example.producer.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.producer.model.IAGProvisioningReqeuestJSON;
import org.example.producer.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/kafka")
@RestController
public class KafkaProducerController {

    @Autowired
    KafkaProducerService kafkaProducerService;

    @Value("${topic.name}")
    private String topicName;

    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(@RequestParam int partition,
                                            @RequestParam(required = false) String key,
                                            @RequestParam String message) {
        try {
            kafkaProducerService.sendMessage(partition, key, message);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error sending message to partition {}: {}", partition, e.getMessage(), e);

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
