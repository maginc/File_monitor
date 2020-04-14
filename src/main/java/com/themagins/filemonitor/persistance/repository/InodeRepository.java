package com.themagins.filemonitor.persistance.repository;

import com.themagins.filemonitor.persistance.model.Inode;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Andris Magins
 * @created 15/01/2020
 **/
public interface InodeRepository extends JpaRepository<Inode, Long> {
}
