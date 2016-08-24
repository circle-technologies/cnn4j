package com.circle_technologies.rnn.naive.command;

import com.circle_technologies.caf.annotation.Nullable;
import com.circle_technologies.caf.io.IOToolKit;
import com.circle_technologies.caf.logging.Log;
import com.circle_technologies.rnn.naive.context.NaiveNetworkContext;
import com.circle_technologies.rnn.naive.network.norm.NetworkNorm;
import com.circle_technologies.rnn.naive.network.norm.SimpleNetworkNorm;
import com.google.gson.Gson;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.io.File;
import java.io.IOException;

/**
 * Created by Sellm on 24.08.2016.
 */
public class CommandRestore extends AbstractNRNNCommand {

    public CommandRestore(NaiveNetworkContext context) {
        super(context);
    }

    @Override
    public Options createOptions() {
        Options options = new Options();
        options.addOption(Option.builder("f").longOpt("file").hasArg().required().build());
        return options;
    }

    @Override
    public String getCommand() {
        return "restore";
    }

    @Override
    public String execute(@Nullable CommandLine commandLine) {

        String dirPath = commandLine.getOptionValue("f");
        File dir = new File(dirPath);
        File net = new File(dir, "rnn.net");
        File norm = new File(dir, "rnn.nor");
        if (!net.isFile() || !norm.isFile()) {
            return "Restore failed: Files missing";
        }


        if (!loadNorm(norm)) {
            return "Restoring norm failed";
        }
        if (!loadNetwork(net)) {
            return "Restoring network failed";
        } else {
            return "Restored from dir: " + dirPath;
        }


    }

    private boolean loadNetwork(File file) {
        boolean restored = getContext().getNetwork().restore(file);
        if (restored) {
            Log.debug("restore", "Restored network");
        }
        return restored;
    }

    private boolean loadNorm(File file) {
        try {
            String json = IOToolKit.readFileToString(file);
            Gson gson = new Gson();
            NetworkNorm norm = gson.fromJson(json, SimpleNetworkNorm.class);
            Log.debug("restore", "Restored norm:  " + gson.toJson(norm));
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
