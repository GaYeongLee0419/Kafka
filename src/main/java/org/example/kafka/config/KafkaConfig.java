package org.example.kafka.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaConfig {
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(DefaultErrorHandler errorHandler) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setCommonErrorHandler(errorHandler());
        return factory;
    }
    @Bean
    public DefaultErrorHandler errorHandler() {
        // FixedBackOff: 재시도 간격과 최대 재시도 횟수 설정 (1000ms 간격, 2번 재시도)
        FixedBackOff fixedBackOff = new FixedBackOff(1000L, 2L);

        // DefaultErrorHandler 생성 및 비재시도 예외 추가
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(fixedBackOff);

        // 비재시도 예외 추가
        errorHandler.addNotRetryableExceptions(IllegalArgumentException.class);

        return errorHandler;
    }


}
