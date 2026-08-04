package com.contractlens.service.integration.message.impl;

import com.contractlens.common.constant.ServiceConstants;
import com.contractlens.common.dto.GatewayTransactionEvent;
import com.contractlens.common.dto.PublishEvent;
import com.contractlens.common.enums.PublishStatus;
import com.contractlens.service.db.redis.service.PublishEventDelegateSvc;
import com.contractlens.service.integration.message.PublishEventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service(ServiceConstants.REDIS_PUBLISH_EVENT)
public class RedisPublishEventImpl implements PublishEventService {


    @Qualifier(ServiceConstants.REDIS_PUBLISH_EVENT_DELEGATE)
    @Autowired
    private  PublishEventDelegateSvc publishEventDelegateSvc;
    private final ObjectMapper objectMapper;
    private static final int MAX_RETRY = 10;

    public RedisPublishEventImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @Override
    public void upsert(GatewayTransactionEvent event) {
        PublishEvent publishEvent = publishEventDelegateSvc.findByTransactionId(event.getTransactionId());

        publishEvent.setTransactionId(event.getTransactionId());

        try {
            publishEvent.setPayload(objectMapper.writeValueAsString(event));
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }

        int retryCount = publishEvent.getRetryCount() == null
                ? 0
                : publishEvent.getRetryCount() + 1;

        publishEvent.setRetryCount(retryCount);

        publishEvent.setNextRetryTime(
                LocalDateTime.now().plusMinutes(1)
        );

        publishEvent.setStatus(
                retryCount >= MAX_RETRY
                        ? PublishStatus.FAILED
                        : PublishStatus.PENDING
        );

        publishEventDelegateSvc.save(publishEvent);
    }

    @Override
    public void markSuccess(UUID transactionId) {
        publishEventDelegateSvc.delete(transactionId);
    }

    @Override
    public List<PublishEvent> findPending() {
        return publishEventDelegateSvc.findPending();
    }
}
