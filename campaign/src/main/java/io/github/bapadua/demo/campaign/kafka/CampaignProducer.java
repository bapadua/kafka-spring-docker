package io.github.bapadua.demo.campaign.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bapadua.demo.campaign.entities.Campaign;
import io.github.bapadua.demo.campaign.utils.Queues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author ice_bpadua on 27/09/2020
 */
@Component
public class CampaignProducer {

    private final KafkaTemplate kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public CampaignProducer(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(final Campaign campaign) throws JsonProcessingException {
        final String key = UUID.randomUUID().toString();
        final String message = objectMapper.writeValueAsString(campaign);
    kafkaTemplate.send(Queues.CAMPAIGN_TOPIC, key, message);
    }

    public void remove(final String id) {
        final String key = UUID.randomUUID().toString();
        kafkaTemplate.send(Queues.CAMPAIGN_TOPIC, key, id);
    }
}
