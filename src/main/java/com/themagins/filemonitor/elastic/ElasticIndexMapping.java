package com.themagins.filemonitor.elastic;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Andris Magins
 * @created 28-Mar-20
 **/
public class ElasticIndexMapping {
    private static Logger LOG = LoggerFactory.getLogger(ElasticIndexMapping.class);

    public static String getStringFromFile(String fileName) throws IOException {
        return FileUtils.readFileToString(new File(fileName), StandardCharsets.UTF_8);
    }
}
