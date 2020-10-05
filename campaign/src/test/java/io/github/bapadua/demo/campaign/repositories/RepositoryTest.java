package io.github.bapadua.demo.campaign.repositories;

import io.github.bapadua.demo.campaign.entities.Campaign;
import io.github.bapadua.demo.campaign.entities.Team;
import io.github.bapadua.demo.campaign.utils.DateUtils;
import io.github.bapadua.demo.campaign.utils.TestUtils;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Description;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * @author ice_bpadua on 26/09/2020
 */

@DataJpaTest
class RepositoryTest {

    @Autowired
    CampaignRepository repository;

    @Autowired
    TeamRepository teamRepository;

    @Test
    void context() {
        Assertions.assertNotNull(repository);
        Assertions.assertNotNull(teamRepository);
    }

    @Test
    @Description(value = "Não deve persistir uma nova campanha sem nome")
    void shouldThrowErrorWhenNoCampaignName() throws Exception {
        Campaign campaign = campaignBuilder();
        campaign.setCampaignName(null);
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> repository.save(campaign));
    }

    @Test
    @Description(value = "Não deve persistir uma nova campanha sem data final")
    void shouldThrowErrorWhenNoEndDate() throws Exception {
        Campaign campaign = campaignBuilder();
        campaign.setCampaignEndDate(null);
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> repository.save(campaign));
    }

    @Test
    @Description(value = "Deve mostrar campanhas não expiradas")
    void showOnlyNonExpiredCampaigns() throws Exception {
        persistedCampaigns();
        final List<Campaign> nonExpired = repository.findNonExpired();
        Assertions.assertEquals(5, nonExpired.size());
    }

    @Test
    void shouldCreateATeam() throws Exception {
        final Team save = teamRepository.save(teamBuilder());
        Assertions.assertNotNull(save.getId());
        Assertions.assertEquals("São Paulo", save.getTeamName());
    }

    @Test
    @Description(value = "Deve retornar as campanhas informando as datas de inicio e fim")
    void shouldRetrieveAllCampaignByDate() throws Exception {
        final Team team = TestUtils.loadFile("campaign/team.json", Team.class);
        final Team save = teamRepository.save(team);

        final List<Campaign> campaigns = Arrays.asList(TestUtils.loadFiles("campaign/campaign-scheduler.json", Campaign[].class));

        campaigns.forEach(campaign -> campaign.setTeam(save));
        repository.saveAll(campaigns);

        final LocalDate startDate = DateUtils.localDateFrom("01/10/2020");
        final LocalDate endDate = DateUtils.localDateFrom("02/10/2021");

        final List<Campaign> list = repository.findAllByCampaignStartDateAndCampaignEndDate(startDate, endDate);
        Assertions.assertEquals(1, list.size());

    }

    private Campaign campaignBuilder() throws Exception {
        return TestUtils.loadFile("campaign/campaign.json", Campaign.class);
    }

    private void persistedCampaigns() throws Exception {
        final Team team = persistedTeam();
        List<Campaign> campaigns = Arrays.asList(TestUtils.loadFiles("campaign/" + "campaigns.json", Campaign[].class));
        campaigns.forEach(campaign -> campaign.setTeam(team));
        Lists.newArrayList(repository.saveAll(campaigns));
    }

    private Team persistedTeam() throws Exception {
        final Team team = TestUtils.loadFile("campaign/team.json", Team.class);
        return teamRepository.save(team);
    }

    private Team teamBuilder() throws Exception {
        return TestUtils.loadFile("campaign/team.json", Team.class);
    }

    private Team teamBuilder(final String file) throws Exception {
        return TestUtils.loadFile("campaign/" + file, Team.class);
    }

}