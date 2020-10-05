package io.github.bapadua.demo.partner.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.github.bapadua.demo.partner.converters.LocalDatePersistenceConverter;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

/**
 * @author ice_bpadua on 26/09/2020
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_campaigns")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CampaignReplica implements Serializable {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(nullable = false)
    private String campaignName;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "team", nullable = false)
    private TeamReplica team;

    @JsonSerialize(using = ToStringSerializer.class)
    @Convert(converter = LocalDatePersistenceConverter.class)
    @Column(nullable = false)
    private LocalDate campaignStartDate;

    @JsonSerialize(using = ToStringSerializer.class)
    @Convert(converter = LocalDatePersistenceConverter.class)
    @Column(nullable = false)
    private LocalDate campaignEndDate;

    @JsonIgnore
    @ManyToMany(mappedBy = "campaigns", fetch = FetchType.LAZY)
    private Set<Partner> partner;

    public CampaignReplica(String campaignName, TeamReplica team, LocalDate campaignStartDate, LocalDate campaignEndDate) {
        this.campaignName = campaignName;
        this.team = team;
        this.campaignStartDate  = campaignStartDate;
        this.campaignEndDate = campaignEndDate;
    }
}
