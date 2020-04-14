package com.themagins.filemonitor.elastic;

import com.themagins.filemonitor.persistance.Inode;
import com.themagins.filemonitor.persistance.model.Catalog;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

@Component
public class Elastic {
    final
    Document document;

    public Elastic(Document document) {
        this.document = document;
    }

    public void indexCatalog(Catalog catalog, List<File> fileList) {

        for(File file: fileList){
            createDocument(catalog, file);


        }
    }

    public void createDocument(Catalog catalog, File file) {
        Inode inode = new Inode();
        String unixLIkeCatalogPath = catalog.getRootPath().replaceAll("\\\\", "/");
        String unixLIkeParentPath = file.getParent().replaceAll("\\\\", "/");
        String filePath = file.getPath().replaceAll("\\\\", "/");

        inode.setName(file.getName());
        inode.setParent(unixLIkeParentPath.replaceAll(unixLIkeCatalogPath, catalog.getName()));
        inode.setIs_file(file.getAbsoluteFile().isFile());
        inode.setId(Instant.now().toEpochMilli());
        inode.setRoot_catalog(catalog.getName());
        inode.setMetadata("lol");
        inode.setSize(file.length());


        inode.setFile_path("/" + filePath.replaceAll(unixLIkeCatalogPath, catalog.getName()));
        try {
            document.save(inode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}