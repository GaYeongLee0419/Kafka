package org.example.consumer.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumerService {

//    @KafkaListener(topicPartitions = @TopicPartition(topic = "${spring.kafka.consumer.topic1}", partitions = {"0"}), groupId = "${spring.kafka.consumer.group-id1}")
    @KafkaListener(topics = "${spring.kafka.consumer.topic1}", groupId = "${spring.kafka.consumer.group-id1}")
    public void consume1_1(ConsumerRecord<String, String> record) {
        log.info("[Consumer Group 1-1] Partition: {}, Offset: {}, Message: {}",
                record.partition(), record.offset(), record.value());
    }

//    @KafkaListener(topicPartitions = @TopicPartition(topic = "${spring.kafka.consumer.topic1}", partitions = {"1"}), groupId = "${spring.kafka.consumer.group-id1}")
    @KafkaListener(topics = "${spring.kafka.consumer.topic1}", groupId = "${spring.kafka.consumer.group-id1}")
    public void consume1_2(ConsumerRecord<String, String> record) {
        log.info("[Consumer Group 1-2] Partition: {}, Offset: {}, Message: {}",
                record.partition(), record.offset(), record.value());
    }

//    @KafkaListener(topicPartitions = @TopicPartition(topic = "${spring.kafka.consumer.topic1}", partitions = {"2"}), groupId = "${spring.kafka.consumer.group-id1}")
    @KafkaListener(topics = "${spring.kafka.consumer.topic1}", groupId = "${spring.kafka.consumer.group-id1}")
    public void consume1_3(ConsumerRecord<String, String> record) {
        log.info("[Consumer Group 1-3] Partition: {}, Offset: {}, Message: {}",
                record.partition(), record.offset(), record.value());
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "${spring.kafka.consumer.topic2}", partitions = {"0"}), groupId = "${spring.kafka.consumer.group-id2}")
    public void consume2_1(ConsumerRecord<String, String> record) {
        log.info("[Consumer Group 2-1] Partition: {}, Offset: {}, Message: {}",
                record.partition(), record.offset(), record.value());
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "${spring.kafka.consumer.topic2}", partitions = {"1"}),
            groupId = "${spring.kafka.consumer.group-id2}")
    public void consume2_2(ConsumerRecord<String, String> record) {
        log.info("[Consumer Group 2-2] Partition: {}, Offset: {}, Message: {}",
                record.partition(), record.offset(), record.value());
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "${spring.kafka.consumer.topic2}", partitions = {"2"}),
            groupId = "${spring.kafka.consumer.group-id2}")
    public void consume2_3(ConsumerRecord<String, String> record) {
        log.info("[Consumer Group 2-3] Partition: {}, Offset: {}, Message: {}",
                record.partition(), record.offset(), record.value());
    }
}
