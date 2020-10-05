package io.github.bapadua.demo.partner.mappers;

import io.github.bapadua.demo.partner.dtos.CampaignDTO;
import io.github.bapadua.demo.partner.dtos.PartnerDTO;
import io.github.bapadua.demo.partner.entities.CampaignReplica;
import io.github.bapadua.demo.partner.entities.Partner;
import io.github.bapadua.demo.partner.entities.TeamReplica;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ice_bpadua on 03/10/2020
 */
@Component
public class PartnerMapper {

    public Partner toEntity(PartnerDTO dto, TeamReplica team, Set<CampaignReplica> campaigns) {
        return Partner.builder()
                .id(dto.getId())
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .birthDate(dto.getBirthDate())
                .team(team)
                .campaigns(campaigns)
                .build();
    }

    public Set<CampaignReplica> toCampaignSet(List<CampaignDTO> campaigns, TeamReplica team) {
        return campaigns.stream()
                .map(campaignDTO -> toCampaign(campaignDTO, team))
                .collect(Collectors.toSet());
    }

    public CampaignReplica toCampaign(final CampaignDTO dto, TeamReplica team) {
        return CampaignReplica.builder()
                .id(dto.getId())
                .campaignName(dto.getCampaignName())
                .campaignStartDate(dto.getStartDate())
                .campaignEndDate(dto.getEndDate())
                .team(team)
                .build();
    }

    public PartnerDTO toDTO (Partner partner) {
        return PartnerDTO
                .builder()
                .id(partner.getId())
                .fullName(partner.getFullName())
                .email(partner.getEmail())
                .teamId(partner.getTeam().getId())
                .birthDate(partner.getBirthDate())
                .teamName(partner.getTeam().getTeamName())
                .campaigns(toCampaignDTOList(partner.getCampaigns()))
                .build();
    }

    public List<CampaignDTO> toCampaignDTOList(Set<CampaignReplica> campaign) {
        return campaign.stream()
                .map(this::toCampaignDTO)
                .collect(Collectors.toList());
    }

    public CampaignDTO toCampaignDTO(CampaignReplica campaign) {
        return CampaignDTO
                .builder()
                .campaignName(campaign.getCampaignName())
                .team(campaign.getTeam().getTeamName())
                .startDate(campaign.getCampaignStartDate())
                .endDate(campaign.getCampaignEndDate())
                .build();
    }
}
