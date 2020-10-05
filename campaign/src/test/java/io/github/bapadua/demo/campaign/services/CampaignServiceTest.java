package io.github.bapadua.demo.campaign.services;

import io.github.bapadua.demo.campaign.entities.Campaign;
import io.github.bapadua.demo.campaign.entities.Team;
import io.github.bapadua.demo.campaign.kafka.CampaignProducer;
import io.github.bapadua.demo.campaign.repositories.CampaignRepository;
import io.github.bapadua.demo.campaign.repositories.TeamRepository;
import io.github.bapadua.demo.campaign.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;

/**
 * @author ice_bpadua on 27/09/2020
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CampaignServiceTest {

    KafkaTemplate kafkaTemplate = Mockito.mock(KafkaTemplate.class);
    CampaignRepository repository = Mockito.mock(CampaignRepository.class);
    TeamRepository teamRepository = Mockito.mock(TeamRepository.class);
    CampaignProducer campaignProducer = new CampaignProducer(kafkaTemplate);
    CampaignService campaignService =new CampaignService(campaignProducer, teamRepository, repository);

    @Test
    void context() {
        Assertions.assertNotNull(kafkaTemplate);
        Assertions.assertNotNull(teamRepository);
        Assertions.assertNotNull(repository);
    }

    void shouldSendAnMessageToTopic() throws Exception {
        final Campaign campaign = campaignBuilder();
        Mockito.when(teamRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(teamBuilder()));
        Mockito.when(repository.save(Mockito.any())).thenReturn(campaign);
        Mockito.when(repository.findAllByCampaignStartDateAndCampaignEndDate(Mockito.any(), Mockito.any()))
                .thenReturn(Collections.emptyList());
        campaignService.create(campaign);
    }

    private Team teamBuilder() throws Exception {
        return TestUtils.loadFile("campaign/team.json", Team.class);
    }

    private Campaign campaignBuilder() throws Exception {
        return TestUtils.loadFile("campaign/campaign.json", Campaign.class);
    }

}
