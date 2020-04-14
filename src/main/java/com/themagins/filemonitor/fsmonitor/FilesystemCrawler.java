package com.themagins.filemonitor.fsmonitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

/**
 * @author Andris Magins
 * @created 16/01/2020
 **/
public class FilesystemCrawler {
    private static Logger LOG = LoggerFactory.getLogger(FilesystemCrawler.class);

    private String path;

    public FilesystemCrawler(String path) {
        this.path = path;

    }

    public List<File> getFiles() {
        LOG.info("Looking for files in " + path + " directory, my take some time depending on file count");
        Path startPath = Paths.get(path);
        List<File> fileList = new ArrayList<>();

        int[] count = {0};
        final int[] failedReads = {0};
        int[] directoryCOunt = {0};
        try {
            long startTime = System.currentTimeMillis();
            Files.walkFileTree(
                    startPath,
                    EnumSet.noneOf(FileVisitOption.class),
                    Integer.MAX_VALUE, new SimpleFileVisitor<Path>() {
                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {

                            File trimmedPath = file.toFile();
                            fileList.add(trimmedPath);
                            ++count[0];

                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult visitFileFailed(Path file, IOException e) {
                            LOG.error("Visiting failed for " + file);
                            failedReads[0]++;
                            return FileVisitResult.SKIP_SUBTREE;
                        }

                        @Override
                        public FileVisitResult preVisitDirectory(Path dir,
                                                                 BasicFileAttributes attrs) {
                            // System.out.printf("About to visit directory %s\n", dir);
                            File trimmedPath = dir.toFile();
                            fileList.add(trimmedPath);
                            ++directoryCOunt[0];

                            return FileVisitResult.CONTINUE;
                        }
                    });
            long finishTime = System.currentTimeMillis();
            LOG.info("Found: " + Arrays.toString(count) + " files and " + Arrays.toString(directoryCOunt) + " directories in " + (finishTime - startTime) + " milliseconds");
            LOG.info("Failed to read " + Arrays.toString(failedReads) + " files");

        } catch (IOException e) {
            LOG.error(e.getMessage());
            // handle exception
        }

        /*
        Converting list to set is MUCH faster than adding each entry directly to set
        */
        return fileList;

    }

}
