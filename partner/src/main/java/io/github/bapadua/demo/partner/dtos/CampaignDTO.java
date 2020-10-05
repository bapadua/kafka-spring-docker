package io.github.bapadua.demo.partner.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author ice_bpadua on 30/09/2020
 */

@Data
@Builder
public class CampaignDTO {

    private UUID id;
    @JsonProperty(value = "campanha")
    private String campaignName;
    @JsonProperty(value = "inicio")
    private LocalDate startDate;
    @JsonProperty(value = "final")
    private LocalDate endDate;
    @JsonProperty(value = "time")
    private String team;
}
