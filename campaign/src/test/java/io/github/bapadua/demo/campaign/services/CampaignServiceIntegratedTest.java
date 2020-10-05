package io.github.bapadua.demo.campaign.services;

import io.github.bapadua.demo.campaign.dtos.CampaignDTO;
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
import org.springframework.context.annotation.Description;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

/**
 * @author ice_bpadua on 26/09/2020
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration
class CampaignServiceIntegratedTest {

    TeamRepository teamRepository = Mockito.mock(TeamRepository.class);
    CampaignRepository campaignRepository = Mockito.mock(CampaignRepository.class);
    CampaignProducer campaignProducer = Mockito.mock(CampaignProducer.class);

    CampaignService service = new CampaignService(campaignProducer, teamRepository, campaignRepository);

    @Test
    void context() {
        Assertions.assertNotNull(service);
        Assertions.assertNotNull(campaignRepository);
        Assertions.assertNotNull(teamRepository);
    }

    @Test
    @Description(value = "Deve inserir uma nova campanha na base de dados")
    void shouldCreateAnCampaign() throws Exception {
        final Campaign campaign = campaignLoader("new-campaign.json");
        Mockito.when(campaignRepository.save(Mockito.any())).thenReturn(campaign);
        final Campaign persisted = service.create(campaign);
        Assertions.assertNotNull(persisted.getId());
    }


    @Test
    @Description("Deve atualizar uma campanha")
    void shouldUpdateAnCampaign() throws Exception {
        Campaign campaign = campaignLoader("new-campaign.json");
        final Campaign updated = campaignLoader("updated-campaign.json");
        Mockito.when(campaignRepository.save(Mockito.any())).thenReturn(updated);
        Mockito.when(campaignRepository.findById(Mockito.any())).thenReturn(Optional.of(campaign));
        final Campaign update = service.update(campaign);
        Assertions.assertNotNull(update.getId());
        Assertions.assertEquals("Campanha Atualizada", update.getCampaignName());
    }

    @Test
    @Description(value = "Deve retornar uma campanha dado um UUID")
    void shouldFindAnCampaign() throws Exception {
        Campaign campaign = campaignLoader("regular-campaign.json");
        Mockito.when(campaignRepository.findById(Mockito.any())).thenReturn(Optional.of(campaign));
        final CampaignDTO found = service.find(campaign.getId().toString());
        Assertions.assertEquals(campaign.getId().toString(), found.getId());
    }


    @Test
    @Description(value = "Deve excluir a campanha da base")
    void shouldDeleteCampaign() throws Exception {
        final Campaign campaign = campaignLoader("new-campaign.json");
        service.delete(campaign.getId().toString());
        Mockito.verify(campaignRepository).deleteById(Mockito.any());
    }

    private Team persistedTeam() throws Exception {
        return TestUtils.loadFile("campaign/team.json", Team.class);
    }

    private Campaign campaignLoader(final String file) throws Exception {
        Campaign campaign = TestUtils.loadFile("campaign/" + file, Campaign.class);
        campaign.setTeam(persistedTeam());
        return campaign;
    }

}