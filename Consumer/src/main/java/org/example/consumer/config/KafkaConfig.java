package org.example.consumer.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.example.consumer.service.OAuth2TokenFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Configuration
@EnableKafka
public class KafkaConfig {

    @Value("${security.protocol}")
    String securityProtocol;

    @Value("${sasl.mechanism}")
    String saslMechanism;

    @Value("${sasl.jaas.config}")
    String saslJaasConfig;

    @Value("${sasl.oauthbearer.token.endpoint.uri}")
    String oauthUri;

    private final AtomicReference<String> currentAccessToken = new AtomicReference<>(); // AtomicReference로 안전한 접근

    @Autowired
    private OAuth2TokenFetcher oAuth2TokenFetcher;

    @Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig(),
                new StringDeserializer(),
                new StringDeserializer());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Scheduled(fixedRate = 3600000) // 1시간마다 토큰 갱신
    public void refreshAccessToken() {
        try {
            String newAccessToken = oAuth2TokenFetcher.getAccessToken();
            currentAccessToken.set(newAccessToken);
            log.info("Access token refreshed: {}", newAccessToken);

            // 새 토큰을 반영하기 위해 ConsumerFactory 갱신
            refreshConsumerFactory();
        } catch (IOException e) {
            log.error("Failed to refresh access token", e);
        }
    }

    private void refreshConsumerFactory() {
        // 업데이트된 accessToken이 반영된 Properties
        Properties newConfig = consumerConfig();

        // 기존 ConsumerFactory를 새로 만든 config로 업데이트
        DefaultKafkaConsumerFactory<String, String> newFactory = new DefaultKafkaConsumerFactory<>(newConfig,
                new StringDeserializer(),
                new StringDeserializer());

        // ConsumerFactory 갱신
        ConcurrentKafkaListenerContainerFactory<String, String> factory = kafkaListenerContainerFactory();
        factory.setConsumerFactory(newFactory);

        // 리스너 컨테이너 재시작
        for (MessageListenerContainer listenerContainer : kafkaListenerEndpointRegistry.getListenerContainers()) {
            listenerContainer.stop(); // 기존 리스너 중지
            listenerContainer.start(); // 새로 시작
        }
    }

    public Properties consumerConfig() {
        String accessToken = currentAccessToken.get();

        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka_broker_url");
        props.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // SASL OAUTHBEARER 인증 설정
        props.setProperty("security.protocol", securityProtocol);
        props.setProperty("sasl.mechanism", saslMechanism);
        props.setProperty("sasl.oauthbearer.token.endpoint.uri", oauthUri);
        props.setProperty("sasl.jaas.config", saslJaasConfig);
        props.setProperty("sasl.oauthbearer.token", accessToken);

        return props;
    }
}
