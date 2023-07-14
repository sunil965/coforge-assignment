package com.coforge.assignment.kafka;


import com.coforge.assignment.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class KafKaProducerService {

    //1. General topic with a string payload
    @Value(value = "${general.topic.name}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    //2. Topic with user object payload
    @Value(value = "${user.topic.name}")
    private String userTopicName;

    @Autowired
    private KafkaTemplate<String, User> userKafkaTemplate;

    public void sendTextMessage(String text) {
        log.info("Sending text to kafka topic {}", topicName);
        CompletableFuture<SendResult<String, String>> future = this.kafkaTemplate.send(topicName, text);
        future.whenComplete((stringUserSendResult, throwable) -> {
            if (stringUserSendResult != null)
                log.info("Sent message: " + text + " with offset: " + stringUserSendResult.getRecordMetadata().offset());
            else if (throwable != null) {
                log.error("Unable to send text : " + text, throwable);
            }
        });

    }

    public void sendUserLogMessage(User user) {
        log.info("Sending text to kafka topic {}", topicName);
        CompletableFuture<SendResult<String, User>> completableFuture = this.userKafkaTemplate.send(userTopicName, user);
        completableFuture.whenComplete((stringUserSendResult, throwable) -> {
            if (stringUserSendResult != null)
                log.info("Sent message: " + user + " with offset: " + stringUserSendResult.getRecordMetadata().offset());
            else if (throwable != null) {
                log.error("Unable to send message : " + user, throwable);
            }
        });
    }

/*
    public void sendUserLogMessage(User user) {
        log.info("Sending text to kafka topic {}", topicName);
        ListenableFuture<SendResult<String, User>> future = (ListenableFuture<SendResult<String, User>>) this.userKafkaTemplate.send(userTopicName, user);
        future.addCallback(new ListenableFutureCallback<SendResult<String, User>>() {
            @Override
            public void onSuccess(SendResult<String, User> result) {
                log.info("User registered: " + user + " with offset: " + result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("User not registered : " + user, ex);
            }
        });
    }*/
}
