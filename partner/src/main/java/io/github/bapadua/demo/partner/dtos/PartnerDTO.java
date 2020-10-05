package io.github.bapadua.demo.partner.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @author ice_bpadua on 30/09/2020
 */

@Data
@Builder
public class PartnerDTO {

    private UUID id;

    @JsonProperty(value = "nome-completo")
    private String fullName;

    @Email(message = "informe um e-mail válido")
    private String email;

    @JsonProperty(value = "data-nascimento")
    private LocalDate birthDate;

    @JsonProperty(value = "codigo-time")

    private Long teamId;

    @JsonProperty(value = "nome-time")
    private String teamName;

    @JsonProperty(value = "campanhas")
    private List<CampaignDTO> campaigns;

}
