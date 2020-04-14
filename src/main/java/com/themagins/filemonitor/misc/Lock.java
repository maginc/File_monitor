package com.themagins.filemonitor.misc;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
/**
 * @author Andris Magins
 * @created 15/01/2020
 **/
public class Lock {

    public static boolean lockInstance(final String lockFile) {
        try {
            final File file = new File(lockFile);
            final RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            final FileLock fileLock = randomAccessFile.getChannel().tryLock();
            if (fileLock != null) {
                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    try {
                        fileLock.release();
                        randomAccessFile.close();
                        file.delete();
                    } catch (Exception e) {
                        System.out.println("Unable to remove lock file: " + lockFile + e);
                    }
                }));
                return true;
            }
        } catch (Exception e) {
            System.out.println("Unable to create and/or lock file: " + lockFile + e);
        }
        return false;
    }

}