package com.circle_technologies.rnn.naive.network;

import java.io.File;
import java.io.IOException;

/**
 * Created by Sellm on 25.08.2016.
 */
public class DirectoryDataAccumulator extends DataAccumulator {

    public long parseDir(String dirPath) throws IOException {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (!dir.isDirectory() || files == null) {
            return 0;
        }

        long dataRead = 0;

        for (File file : files) {
            dataRead += parseJson(file.getAbsolutePath());
        }

        return dataRead;
    }

    public long parseAuto(String path) throws IOException {
        File file = new File(path);
        if (file.isDirectory()) {
            return parseDir(path);
        } else if (file.isFile()) {
            return parseJson(path);
        } else throw new IOException("File is not a dir nor a file");
    }
}
