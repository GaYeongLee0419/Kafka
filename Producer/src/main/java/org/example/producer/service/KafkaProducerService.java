package org.example.producer.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.TopicDescription;
import org.example.producer.model.IAGProvisioningReqeuestJSON;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final AdminClient adminClient;

    @Value("${topic.name}")
    private String topicName;

    private int partitionCount;

    @PostConstruct
    public void init() {
        try {
            TopicDescription description = adminClient.describeTopics(Collections.singletonList(topicName))
                    .all().get().get(topicName);
            partitionCount = description.partitions().size();
            log.info("Topic {} has {} partitions", topicName, partitionCount);
        } catch (ExecutionException | InterruptedException e) {
            log.error("Failed to retrieve topic information for {}", topicName, e);
        }
    }

    public void sendMessage(int partition, String key, String message) {
        log.info("Sending message to topic {} partition {} with key {}", topicName, partition, key);
        kafkaTemplate.send(topicName, partition, key, message);
    }
}
