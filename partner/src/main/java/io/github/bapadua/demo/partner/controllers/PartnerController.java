package io.github.bapadua.demo.partner.controllers;

import io.github.bapadua.demo.partner.dtos.PartnerDTO;
import io.github.bapadua.demo.partner.mappers.PartnerMapper;
import io.github.bapadua.demo.partner.services.PartnerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * @author ice_bpadua on 02/10/2020
 */
@RestController
@RequestMapping("v1")
@Api(value = "Api sócio torcedor")
public class PartnerController {

    private final PartnerService partnerService;
    private final PartnerMapper partnerMapper;

    public PartnerController(PartnerService partnerService, PartnerMapper partnerMapper) {
        this.partnerService = partnerService;
        this.partnerMapper = partnerMapper;
    }

    @PostMapping
    @ApiOperation(value = "Cadastra um socio torcedor")
    public ResponseEntity<PartnerDTO> create(final @Validated @RequestBody PartnerDTO request) {
        return ResponseEntity.ok(
                partnerMapper.toDTO(
                        partnerService.create(request)));
    }

    @PostMapping("/join/{partnerid}/campaign/{campaignid}")
    public ResponseEntity<Void> joinCampaign(@PathVariable("partnerid") String partnerid,
                                             @PathVariable("campaignid") String campaignid) {
        partnerService.join(UUID.fromString(partnerid), UUID.fromString(campaignid));
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<PartnerDTO> find(@PathVariable("id") String id) {
        return ResponseEntity.ok(
                partnerMapper.toDTO(
                        partnerService.find(UUID.fromString(id))));
    }
}
