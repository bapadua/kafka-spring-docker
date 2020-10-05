package io.github.bapadua.demo.campaign.controllers;

import io.github.bapadua.demo.campaign.dtos.CampaignDTO;
import io.github.bapadua.demo.campaign.entities.Team;
import io.github.bapadua.demo.campaign.services.CampaignService;
import io.github.bapadua.demo.campaign.services.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ice_bpadua on 26/09/2020
 */
@RestController
public class CampaignController {

    private final CampaignService campaignService;
    private final TeamService teamService;

    public CampaignController(CampaignService campaignService, TeamService teamService) {
        this.campaignService = campaignService;
        this.teamService = teamService;
    }

    @PostMapping
    public ResponseEntity<CampaignDTO> create(final @RequestBody CampaignDTO request) {
        return ResponseEntity.ok(campaignService.process(request));
    }

    @PutMapping
    public ResponseEntity<CampaignDTO> update(final @RequestBody CampaignDTO request) {
        return ResponseEntity.ok(campaignService.process(request));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(final @PathVariable("id") String id) {
        campaignService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<CampaignDTO> read(final @PathVariable("id") String id) {
        return ResponseEntity.ok(campaignService.find(id));
    }

    @GetMapping("campaigns")
    public ResponseEntity<List<CampaignDTO>> list() {
        return ResponseEntity.ok(campaignService.list());
    }

    @GetMapping("teams")
    public ResponseEntity<List<Team>> teams() {
        return ResponseEntity.ok(teamService.list());
    }

}
