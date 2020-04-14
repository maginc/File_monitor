package com.themagins.filemonitor.utilities;

/**
 * @author Andris Magins
 * @created 17/01/2020
 **/
public class PathUtil {

    public static String generateDisplayPath(String rootName,String rootPath, String path){
        String result = "";
        result = path.replaceAll(rootPath,"").trim();
        return rootName + result;
    }
}
