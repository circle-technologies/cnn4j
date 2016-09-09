/*
 * Copyright 2016 Sebastian Sellmair, Thomas Gilli
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.circle_technologies.cnn4j.predictive.network;

import com.circle_technologies.cnn4j.predictive.context.NetworkContext;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;

/**
 * Created by Sellm on 25.08.2016.
 */
public class DirectoryDataAccumulator extends DataAccumulator {

    @Inject
    DirectoryDataAccumulator(NetworkContext context) {
        super(context);
    }

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
