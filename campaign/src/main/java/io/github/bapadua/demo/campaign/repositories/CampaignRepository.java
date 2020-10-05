package io.github.bapadua.demo.campaign.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @author ice_bpadua on 26/09/2020
 */
@Repository
public interface CampaignRepository extends PagingAndSortingRepository<io.github.bapadua.demo.campaign.entities.Campaign, UUID> {

    @Query("select c from Campaign c where c.campaignEndDate >= CURRENT_DATE")
    List<io.github.bapadua.demo.campaign.entities.Campaign> findNonExpired();

    List<io.github.bapadua.demo.campaign.entities.Campaign> findAllByCampaignStartDateAndCampaignEndDate(LocalDate startDate, LocalDate endDate);
}
