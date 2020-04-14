package com.themagins.filemonitor.processor;

import com.themagins.filemonitor.elastic.Elastic;
import com.themagins.filemonitor.elastic.Document;
import com.themagins.filemonitor.persistance.DatabaseOperations;
import com.themagins.filemonitor.persistance.model.Catalog;
import com.themagins.filemonitor.persistance.model.Inode;
import com.themagins.filemonitor.persistance.model.Metadata;
import com.themagins.filemonitor.persistance.model.Path;
import com.themagins.filemonitor.utilities.PathUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @author Andris Magins
 * @created 16/01/2020
 **/
@Component
public class FileProcessor {

    private List<File> listOfFiles;
    private Catalog catalog;

    final
    Document document;

    public FileProcessor(Catalog catalog, Document document) {
        this.catalog = catalog;
        this.document = document;
    }

    public void process(List<File> listOfFiles){

        Elastic elastic = new Elastic(document);
        System.out.println(Arrays.toString(listOfFiles.toArray()));
        DatabaseOperations databaseOperations = new DatabaseOperations();

        listOfFiles.forEach(file -> {
            Metadata metadata = new Metadata();
            Path path = new Path();
            Inode inode = new Inode();


            path.setReal_path(file.getAbsolutePath());
            path.setDisplayPath(
                    PathUtil.generateDisplayPath(catalog.getName(),
                            FilenameUtils.separatorsToUnix(catalog.getRootPath()),
                            FilenameUtils.separatorsToUnix(file.getPath()))
            );
            path.setUri(file.toURI().toString());
            path.setCatalog(catalog.getName());
            path.setMetadata("{\"name\": \""+file.getName()+"\", \"parentfile\": \""+file.getParentFile()+"\"}");

            inode.setFileSize(file.length());
            inode.setFileType("file");

                databaseOperations.insertNewFile(metadata, inode , path);
                elastic.createDocument(catalog,file);



        });


    }

}
