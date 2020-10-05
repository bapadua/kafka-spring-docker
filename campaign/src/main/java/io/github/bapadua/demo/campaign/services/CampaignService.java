package io.github.bapadua.demo.campaign.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.bapadua.demo.campaign.dtos.CampaignDTO;
import io.github.bapadua.demo.campaign.entities.Campaign;
import io.github.bapadua.demo.campaign.entities.Team;
import io.github.bapadua.demo.campaign.kafka.CampaignProducer;
import io.github.bapadua.demo.campaign.repositories.CampaignRepository;
import io.github.bapadua.demo.campaign.repositories.TeamRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author ice_bpadua on 26/09/2020
 */
@Slf4j
@Service
public class CampaignService {

    public static final long CAMPAIGN_EXTENSION = 1L;

    private final CampaignProducer campaignProducer;
    private final TeamRepository teamRepository;
    private final CampaignRepository repository;

    public CampaignService(CampaignProducer campaignProducer, TeamRepository teamRepository,
                           CampaignRepository repository) {
        this.campaignProducer = campaignProducer;
        this.teamRepository = teamRepository;
        this.repository = repository;
    }

    /**
     * Metodo o qual processa a base em busca de campanhas com a mesma data vigente.
     *
     * @param campaign campanha para ser validada
     * @return a mesma campanha
     */
    public Campaign scheduleCampaign(final Campaign campaign) {
        final LocalDate campaignStartDate = campaign.getCampaignStartDate();
        final LocalDate campaignEndDate = campaign.getCampaignEndDate();

        final List<Campaign> campaigns = repository.findAllByCampaignStartDateAndCampaignEndDate(campaignStartDate, campaignEndDate);
        if (campaigns.size() > 1) {
            campaigns.stream()
                    .filter(campaignId -> Boolean.FALSE.equals(campaignId.getId().equals(campaign.getId())))
                    .forEach(this::extendCampaign);
        }
        try {
            log.info("enviando campanha para a fila");
            campaignProducer.send(campaign);
        } catch (JsonProcessingException e) {
            log.error("Erro ao enviar atualização para a fila");
        }

        return campaign;
    }

    /**
     * Altera a campanha, faz uma busca nas campanhas abertas com a mesma data
     * adiciona extende a campanha se for necessário
     *
     * @param campaign campanha a ser extendida
     * @return Campanha atualizada
     */
    private Campaign extendCampaign(Campaign campaign) {
        campaign.setCampaignEndDate(campaign.getCampaignEndDate().plusDays(CAMPAIGN_EXTENSION));
        return scheduleCampaign(update(campaign));
    }

    /**
     * Metodo que retorna somente as campanhas ativas, não vencidas
     *
     * @return List campanhas data > data atual
     */
    public List<CampaignDTO> list() {
        return repository.findNonExpired()
                .stream().map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public CampaignDTO find(final String uuid) {
        Campaign campaign = repository.findById(UUID.fromString(uuid)).orElseThrow(RuntimeException::new);
        return mapToDTO(campaign);
    }

    /**
     * Processa uma campanha, caso ela não exista
     * ou o id informado seja inválido, cria uma nova campanha.
     * caso o id exista apenas atualiza.
     *
     * @param dto
     * @return
     */
    public CampaignDTO process(final CampaignDTO dto) {
        Campaign campaign;
        if (Objects.isNull(dto.getId())) {
            campaign = create(mapToCampaign(dto));
        } else {
            try {
                repository.findById(UUID.fromString(dto.getId())).orElseThrow(RuntimeException::new);
                campaign = update(mapToCampaign(dto));
            } catch (Exception e) {
                log.error("Erro ao encontrar a campanha {}", e.getMessage());
                campaign = create(mapToCampaign(dto));
            }

        }
        return mapToDTO(campaign);
    }

    private Team findTeam(final CampaignDTO campaignDTO) {
        //TODO exception
        return teamRepository.findById(campaignDTO.getTeamid()).orElseThrow(RuntimeException::new);
    }

    public Campaign create(final Campaign campaign) {
        return scheduleCampaign(repository.save(campaign));
    }

    @Transactional
    public Campaign update(final Campaign campaign) {

        Campaign update = repository.findById(campaign.getId()).orElseThrow(RuntimeException::new);
        update.setCampaignEndDate(campaign.getCampaignEndDate());
        update.setCampaignName(campaign.getCampaignName());
        update.setTeam(campaign.getTeam());
        return scheduleCampaign(repository.save(update));
    }

    public void delete(final String id) {
        repository.deleteById(UUID.fromString(id));
    }

    private Campaign mapToCampaign(CampaignDTO dto) {
        UUID uuid = null;
        if (dto.getId() != null) {
            uuid = UUID.fromString(dto.getId());
        }
        return Campaign.builder()
                .id(uuid)
                .campaignName(dto.getCampaign())
                .campaignStartDate(dto.getStartDate())
                .campaignEndDate(dto.getEndDate())
                .team(findTeam(dto))
                .build();
    }

    private CampaignDTO mapToDTO(Campaign campaign) {
        return CampaignDTO
                .builder()
                .id(campaign.getId().toString())
                .teamid(campaign.getTeam().getId())
                .startDate(campaign.getCampaignStartDate())
                .endDate(campaign.getCampaignEndDate())
                .campaign(campaign.getCampaignName())
                .build();
    }
}
