package com.themagins.filemonitor.persistance;

import com.themagins.filemonitor.ApplicationContextUtils;
import com.themagins.filemonitor.persistance.model.Inode;
import com.themagins.filemonitor.persistance.model.Metadata;
import com.themagins.filemonitor.persistance.model.Path;
import com.themagins.filemonitor.persistance.repository.InodeRepository;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author Andris Magins
 * @created 16/01/2020
 **/
@Component
public class DatabaseOperations {

    InodeRepository inodeRepository;
    private ApplicationContext appCtx = ApplicationContextUtils
            .getApplicationContext();

    public DatabaseOperations(){

    }

    public  void insertNewFile(Metadata metadata, Inode inode, Path path){

       inodeRepository = appCtx.getBean(InodeRepository.class);

        path.setInode(inode);

        metadata.setInode(inode);

        inode.setMetadata(metadata);
        inode.setPath(path);
        inodeRepository.save(inode);

    }
}
