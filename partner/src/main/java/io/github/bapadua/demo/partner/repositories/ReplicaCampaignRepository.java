package io.github.bapadua.demo.partner.repositories;

import io.github.bapadua.demo.partner.entities.CampaignReplica;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @author ice_bpadua on 26/09/2020
 */
@Repository
public interface ReplicaCampaignRepository extends PagingAndSortingRepository<CampaignReplica, UUID> {

    @Query("select c from CampaignReplica c where c.campaignEndDate >= CURRENT_DATE")
    List<CampaignReplica> findNonExpired();

    @Query("select c from CampaignReplica c where c.campaignEndDate >= CURRENT_DATE and c.team.id = :teamId")
    List<CampaignReplica> findNonExpiredByTeam(Long teamId);

    List<CampaignReplica> findAllByCampaignStartDateAndCampaignEndDate(LocalDate startDate, LocalDate endDate);
}
