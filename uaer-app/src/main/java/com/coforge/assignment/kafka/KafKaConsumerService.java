package com.coforge.assignment.kafka;

import com.coforge.assignment.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafKaConsumerService {
    @KafkaListener(topics = "${general.topic.name}",  groupId = "${general.topic.group.id}")
    public void consume(String message) {
        log.info(String.format("Message received by kafka consumer-> %s", message));
    }

    @KafkaListener(topics = "${user.topic.name}", groupId = "${user.topic.group.id}",  containerFactory = "userKafkaListenerContainerFactory")
    public void consume(User user) {
        log.info(String.format("User received by consumer -> %s", user));
    }
}
