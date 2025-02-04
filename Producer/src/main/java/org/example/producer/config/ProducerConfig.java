package org.example.producer.config;

import org.springframework.kafka.support.serializer.JsonSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.producer.model.IAGProvisioningReqeuestJSON;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProducerConfig {
    private final Environment env;

    public ProducerConfig(Environment env) {
        this.env = env;
    }

    public Map<String, Object> producerConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, env.getProperty("spring.kafka.producer.bootstrap-servers"));
        config.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return config;
    }

    // Producer를 생성하는 역할
    public ProducerFactory<String, IAGProvisioningReqeuestJSON> producerFactory() {
        return new DefaultKafkaProducerFactory<>(this.producerConfig());
    }

    // Kafka Broker 에 메세지를 보내는 역할을 위한 KafkaTemplate을 반환, KafkaTemplate은 ProducerFactory를 통해 생성된 프로듀서를 사용
    @Bean
    public KafkaTemplate<String, IAGProvisioningReqeuestJSON> kafkaTemplate() {
        return new KafkaTemplate<>(this.producerFactory());
    }
}
