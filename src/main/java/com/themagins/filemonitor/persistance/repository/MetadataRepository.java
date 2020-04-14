package com.themagins.filemonitor.persistance.repository;

import com.themagins.filemonitor.persistance.model.Metadata;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Andris Magins
 * @created 16/01/2020
 **/
public interface MetadataRepository extends JpaRepository<Metadata, Long> {
}
