package com.themagins.filemonitor.fsmonitor;

import com.themagins.filemonitor.elastic.Document;
import com.themagins.filemonitor.persistance.model.Catalog;
import com.themagins.filemonitor.processor.FileProcessor;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

//This service monitors file system and sends newly discovered files for analyzing
/**
 * @author Andris Magins
 * @created 15/01/2020
 **/

//TODO make these as independent component

@Component
public class FileMonitor implements Runnable {
Document document;

    public FileMonitor() {
    }

    private static Logger LOG = LoggerFactory.getLogger(FileMonitor.class);


    private String folderPath;
    private int pollInterval;
    private String catalogName;
    private Catalog catalog;

    public FileMonitor(Catalog catalog, int pollInterval,Document document){
        this.folderPath = catalog.getRootPath();
        this.pollInterval = pollInterval;
        this.catalogName = catalog.getName();
        this.catalog = catalog;
        this.document = document;

    }


    @Override
     public void run() {


        LOG.info("File monitor service started");
        LOG.info("Monitoring \"" + folderPath + "\" folder");

        //Create a FileFilter
        IOFileFilter directories = FileFilterUtils.and(
                FileFilterUtils.directoryFileFilter(),
                HiddenFileFilter.VISIBLE);
        IOFileFilter files = FileFilterUtils.and(
                FileFilterUtils.fileFileFilter(),
                HiddenFileFilter.VISIBLE);
        IOFileFilter filter = FileFilterUtils.or(directories, files);

        /*
            File extension filters can be added there
         */

        FileAlterationObserver observer = new FileAlterationObserver(folderPath);//, filter);
        FileAlterationMonitor monitor = new FileAlterationMonitor(pollInterval);
        FileAlterationListener listener = new FileAlterationListenerAdaptor() {

            int dataListSize = 0;
            int tempListSize = 0;
            List<File> createdFileList = new ArrayList<>();
            List<File> deletedFileList = new ArrayList<>();

            @Override
            public void onStop(FileAlterationObserver var1){

                dataListSize = createdFileList.size();

                if (dataListSize != 0 || tempListSize != 0) {
                    if (dataListSize == tempListSize) {

                        LOG.info("DO analyzing");
                        LOG.info("LIST From monitor: " +  createdFileList.toString());

                        new FileProcessor(catalog, document).process(createdFileList);

                        createdFileList.clear();
                        tempListSize = 0;
                        dataListSize = 0;
                    } else if (dataListSize != 0) {
                        tempListSize = dataListSize;
                    }
                }

            }

            @Override
            public void onFileCreate(File file) {
                LOG.debug("File " + file.getAbsolutePath() + "created ");


                    createdFileList.add(file);
                    LOG.info("File: " + file.getAbsolutePath() + " created");
                    LOG.info("File: " + file.getName() + " size is: " +file.length() + " bytes");

            }

            @Override
            public void onFileDelete(File file) {
                 LOG.debug("File: " + file.getAbsolutePath() + " deleted");

                 deletedFileList.add(file);
            }

            @Override
            public void onFileChange(File file) {
                 LOG.debug("File: " + file.getName() + " changed");
            }


        };
        observer.addListener(listener);
        monitor.addObserver(observer);
        try {
            monitor.start();
        } catch (Exception e) {
            LOG.error(e.toString());
        }

    }

}

