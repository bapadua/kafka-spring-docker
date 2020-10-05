package io.github.bapadua.demo.partner.repositories;

import io.github.bapadua.demo.partner.entities.Partner;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author ice_bpadua on 02/10/2020
 */
@Repository
public interface PartnerRepository extends PagingAndSortingRepository <Partner, UUID> {

}
