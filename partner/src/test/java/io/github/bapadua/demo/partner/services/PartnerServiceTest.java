package io.github.bapadua.demo.partner.services;

import io.github.bapadua.demo.partner.mappers.PartnerMapper;
import io.github.bapadua.demo.partner.repositories.PartnerRepository;
import io.github.bapadua.demo.partner.repositories.ReplicaCampaignRepository;
import io.github.bapadua.demo.partner.repositories.ReplicaTeamRepository;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author ice_bpadua on 02/10/2020
 */
@SpringBootTest
class PartnerServiceTest {

    ReplicaTeamRepository teamRepository = Mockito.mock(ReplicaTeamRepository.class);
    ReplicaCampaignRepository campaignRepository = Mockito.mock(ReplicaCampaignRepository.class);
    PartnerRepository partnerRepository = Mockito.mock(PartnerRepository.class);
    private PartnerMapper partnerMapper = new PartnerMapper();
    PartnerService partnerService = new PartnerService(campaignRepository, teamRepository, partnerRepository, partnerMapper);


}