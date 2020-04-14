package com.themagins.filemonitor.elastic;

import com.themagins.filemonitor.persistance.Inode;

import java.io.IOException;

/**
 * @author Andris Magins
 * @created 28-Mar-20
 **/
public interface DocumentOperations {
    void save(Inode inode) throws IOException;
    void delete(Inode inode);
}
