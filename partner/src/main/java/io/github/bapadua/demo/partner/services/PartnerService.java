package io.github.bapadua.demo.partner.services;

import io.github.bapadua.demo.partner.dtos.PartnerDTO;
import io.github.bapadua.demo.partner.entities.CampaignReplica;
import io.github.bapadua.demo.partner.entities.Partner;
import io.github.bapadua.demo.partner.entities.TeamReplica;
import io.github.bapadua.demo.partner.mappers.PartnerMapper;
import io.github.bapadua.demo.partner.repositories.PartnerRepository;
import io.github.bapadua.demo.partner.repositories.ReplicaCampaignRepository;
import io.github.bapadua.demo.partner.repositories.ReplicaTeamRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author ice_bpadua on 02/10/2020
 */
@Service
public class PartnerService {

    private final ReplicaCampaignRepository campaignRepository;
    private final ReplicaTeamRepository teamRepository;
    private final PartnerRepository partnerRepository;
    private final PartnerMapper partnerMapper;

    public PartnerService(ReplicaCampaignRepository campaignRepository, ReplicaTeamRepository teamRepository, PartnerRepository partnerRepository, PartnerMapper partnerMapper) {
        this.campaignRepository = campaignRepository;
        this.teamRepository = teamRepository;
        this.partnerRepository = partnerRepository;
        this.partnerMapper = partnerMapper;
    }

    public Partner create(final PartnerDTO request) {
        return partnerRepository.save(
                partnerMapper
                        .toEntity(
                                request,
                                findTeam(request.getTeamId()),
                                findCampaigns(request.getTeamId())));
    }

    public Partner find(final UUID id) {
        return partnerRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    private CampaignReplica findCampaign(final UUID id) {
        return campaignRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    private TeamReplica findTeam(final Long id) {
        return teamRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    private Set<CampaignReplica> findCampaigns(final Long id) {
        return campaignRepository.findNonExpiredByTeam(id)
                .stream()
                .collect(Collectors.toSet());
    }

    public void join(UUID partnerid, UUID campaignid) {
        final CampaignReplica campaign = findCampaign(campaignid);
        final Partner partner = find(partnerid);
        partner.getCampaigns().add(campaign);
        partnerRepository.save(partner);
    }
}
