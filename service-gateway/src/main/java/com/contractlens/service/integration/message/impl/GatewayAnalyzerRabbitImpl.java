package com.contractlens.service.integration.message.impl;

import com.contractlens.common.constant.RabbitConstants;
import com.contractlens.common.constant.ServiceConstants;
import com.contractlens.common.dto.GatewayTransactionEvent;
import com.contractlens.common.dto.PublishEvent;
import com.contractlens.service.integration.message.PublishEventService;
import com.contractlens.service.integration.message.RabbitEventPublisher;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(ServiceConstants.GATEWAY_ANALYZER_PUBLISHER)
@Slf4j
public class GatewayAnalyzerRabbitImpl implements RabbitEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    @Qualifier(ServiceConstants.REDIS_PUBLISH_EVENT)
    @Autowired
    private  PublishEventService publishEventService;
    private final ObjectMapper objectMapper;

    public GatewayAnalyzerRabbitImpl(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    @Retryable(
            retryFor = AmqpException.class,
            maxAttempts = 3,
            backoff = @Backoff(
                    delay = 1000,
                    multiplier = 2
            )
    )
    public void publish(GatewayTransactionEvent event) {

        rabbitTemplate.convertAndSend(
                RabbitConstants.ANALYZER_EXCHANGE,
                RabbitConstants.ANALYZER_ROUTING,
                event
        );


    }

    @Override
    public void recover(AmqpException ex, GatewayTransactionEvent event) {
        log.error(
                "Failed publish {}",
                event.getTransactionId(),
                ex
        );

        publishEventService.upsert(event);
    }

    @Override
    @Scheduled(fixedDelay = 60000)
    public void retry() {

        List<PublishEvent> events =
                publishEventService.findPending();

        for (PublishEvent publishEvent : events) {
            try {
                GatewayTransactionEvent event =
                        objectMapper.readValue(
                                publishEvent.getPayload(),
                                GatewayTransactionEvent.class
                        );
                publish(event);
                publishEventService.markSuccess(publishEvent.getTransactionId());
            } catch (Exception ex) {
                log.info("",ex);
            }

        }

    }

}
