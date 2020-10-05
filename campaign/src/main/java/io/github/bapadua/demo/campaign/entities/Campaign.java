package io.github.bapadua.demo.campaign.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.github.bapadua.demo.campaign.converters.LocalDatePersistenceConverter;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
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
public class Campaign implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(nullable = false)
    private String campaignName;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "team", nullable = false)
    private Team team;

    @JsonSerialize(using = ToStringSerializer.class)
    @Convert(converter = LocalDatePersistenceConverter.class)
    @Column(nullable = false)
    private LocalDate campaignStartDate;

    @JsonSerialize(using = ToStringSerializer.class)
    @Convert(converter = LocalDatePersistenceConverter.class)
    @Column(nullable = false)
    private LocalDate campaignEndDate;

    public Campaign(String campaignName, Team team, LocalDate campaignStartDate, LocalDate campaignEndDate) {
        this.campaignName = campaignName;
        this.team = team;
        this.campaignStartDate  = campaignStartDate;
        this.campaignEndDate = campaignEndDate;
    }
}
