package io.github.bapadua.demo.partner.services;

import io.github.bapadua.demo.partner.entities.CampaignReplica;
import io.github.bapadua.demo.partner.repositories.ReplicaCampaignRepository;
import io.github.bapadua.demo.partner.repositories.ReplicaTeamRepository;
import org.springframework.stereotype.Service;

/**
 * @author ice_bpadua on 29/09/2020
 */
@Service
public class CampaignService {

    private final ReplicaCampaignRepository repository;
    private final ReplicaTeamRepository teamRepository;

    public CampaignService(ReplicaCampaignRepository repository, ReplicaTeamRepository teamRepository) {
        this.repository = repository;
        this.teamRepository = teamRepository;
    }

    public void createOrUpdate(final CampaignReplica request) {
        final CampaignReplica campaign = repository.save(request);
    }

}
