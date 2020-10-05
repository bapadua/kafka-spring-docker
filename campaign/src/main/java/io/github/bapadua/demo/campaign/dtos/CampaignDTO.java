package io.github.bapadua.demo.campaign.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Informar as datas no formato d/MM/aaaa
 * @author ice_bpadua on 27/09/2020
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CampaignDTO {

    private String id;
    @JsonProperty("nome-campanha")
    private String campaign;
    @JsonProperty("codigo-time")
    private Long teamid;
    @JsonProperty("inicio-campanha")
    private LocalDate startDate;
    @JsonProperty("final-campanha")
    private LocalDate endDate;
}
