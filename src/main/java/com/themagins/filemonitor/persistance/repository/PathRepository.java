package com.themagins.filemonitor.persistance.repository;

import com.themagins.filemonitor.persistance.model.Path;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PathRepository extends JpaRepository<Path, Long> {

    List<Path> findAllByCatalog(String catalogName);

    List<String> findDisplayPathByCatalog(String name);

    @Query("SELECT p.displayPath FROM Path p WHERE p.catalog =:catalogName")
    List<String> getDisplayPaths(@Param("catalogName") String catalogName );
}
