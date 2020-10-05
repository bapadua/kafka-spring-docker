package io.github.bapadua.demo.partner.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.bapadua.demo.partner.entities.CampaignReplica;
import io.github.bapadua.demo.partner.services.CampaignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static io.github.bapadua.demo.partner.utils.Queues.CAMPAIGN_TOPIC;

/**
 * @author ice_bpadua on 27/09/2020
 */
@Slf4j
@Component
public class CampaignConsumer {


    private final ObjectMapper objectMapper = new ObjectMapper();
    private final CampaignService campaignService;

    public CampaignConsumer(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @KafkaListener(topics = CAMPAIGN_TOPIC, groupId = "partner-campaign")
    public void consumer(String message) throws JsonProcessingException {
        log.info("atualizacao de campanha\n {}\n", message);
        campaignService.createOrUpdate(map(message));
    }

    private CampaignReplica map(final String message) throws JsonProcessingException {
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.readValue(message, CampaignReplica.class);
    }
}
