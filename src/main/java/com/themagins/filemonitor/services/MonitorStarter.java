package com.themagins.filemonitor.services;


import com.themagins.filemonitor.elastic.Document;
import com.themagins.filemonitor.fsmonitor.FileMonitor;
import com.themagins.filemonitor.persistance.model.Catalog;
import com.themagins.filemonitor.persistance.repository.CatalogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

/**
 * @author Andris Magins
 * @created 15/01/2020
 **/
@Service
public class MonitorStarter {
    private static Logger LOG = LoggerFactory.getLogger(MonitorStarter.class);

    @Autowired
    CatalogRepository catalogRepository;
    @Autowired
    Document document;

    @PostConstruct
    public void init(){


        // new AnalyzerConfig(axleCatalogInfoRepository).initConfig();

        //TODO make this working on linux.
        //hint: issue can be caused by permissions to write in root folder
        //path to lockfile could be cadged to something else
        try {
            /*
            if(Lock.lockInstance("/lockfile.loc")){
                LOG.info( "This is only instance of  file monitor");
            }else {
                LOG.info("There is other instance of file monitor running already");
                LOG.info("This instance will be shut down");
                System.exit(0);
            }


             */
            /*
                Start file monitors
                //TODO write code witch will make separate monitor processes per each share
             */

            List<Catalog> catalogPaths = catalogRepository.findAll();

            catalogPaths.forEach(catalog -> {
                new Thread(new FileMonitor(catalog, 2000,document)).start();
            });



           // new FilesystemCrawler("X:\\Test").getFiles();



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void cleanup() {
        /**
         try {
         //  watcher.close();
         } catch (IOException e) {
         LOG.error("Error closing watcher service", e);
         }
         //   executor.shutdown();
         **/
    }

}
