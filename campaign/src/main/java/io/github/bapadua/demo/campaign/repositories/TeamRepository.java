package io.github.bapadua.demo.campaign.repositories;

import io.github.bapadua.demo.campaign.entities.Team;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ice_bpadua on 26/09/2020
 */
@Repository
public interface TeamRepository extends PagingAndSortingRepository<Team, Long> {

}
