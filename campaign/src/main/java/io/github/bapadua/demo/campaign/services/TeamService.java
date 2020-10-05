package io.github.bapadua.demo.campaign.services;

import io.github.bapadua.demo.campaign.entities.Team;
import io.github.bapadua.demo.campaign.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ice_bpadua on 27/09/2020
 */
@Service
public class TeamService {

    private final TeamRepository repository;

    @Autowired
    public TeamService(TeamRepository repository) {
        this.repository = repository;
    }

    public List<Team> list() {
        List<Team> teams = new ArrayList<>();
        repository.findAll().spliterator().forEachRemaining(teams::add);
        return teams;
    }
}
