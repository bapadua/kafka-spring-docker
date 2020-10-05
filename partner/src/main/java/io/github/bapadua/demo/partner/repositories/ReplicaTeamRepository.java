package io.github.bapadua.demo.partner.repositories;

import io.github.bapadua.demo.partner.entities.CampaignReplica;
import io.github.bapadua.demo.partner.entities.TeamReplica;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ice_bpadua on 26/09/2020
 */
@Repository
public interface ReplicaTeamRepository extends PagingAndSortingRepository<TeamReplica, Long> {

}
