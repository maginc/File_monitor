package com.themagins.filemonitor.persistance.repository;

import com.themagins.filemonitor.persistance.model.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Andris Magins
 * @created 17/01/2020
 **/
@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Long> {


    Catalog findByName(String name);

}
