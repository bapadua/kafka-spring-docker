package io.github.bapadua.demo.partner.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ice_bpadua on 26/09/2020
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_teams")
public class TeamReplica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String teamName;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "team")
    private List<CampaignReplica> campaigns = new ArrayList<>();
}
