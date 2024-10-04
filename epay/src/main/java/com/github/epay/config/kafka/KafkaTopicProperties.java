package com.github.epay.config.kafka;

import com.github.epay.domain.enums.KafkaTopic;
import com.github.epay.domain.exception.ExceptionEnum;
import com.github.epay.domain.exception.impl.CustomException;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;
import java.util.Optional;

/**
 * @author Raman Haurylau
 */
@Setter
@ConfigurationProperties("topic")
public class KafkaTopicProperties {
    private Map<KafkaTopic, String> names;

    public String getTopic(KafkaTopic kafkaTopic) {
        return Optional.ofNullable(names.get(kafkaTopic))
                .orElseThrow(() -> new CustomException(ExceptionEnum.ENDPOINT_EXCEPTION));
    }
}
