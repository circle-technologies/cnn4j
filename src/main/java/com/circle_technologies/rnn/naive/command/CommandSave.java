package com.circle_technologies.rnn.naive.command;

import com.circle_technologies.caf.annotation.Nullable;
import com.circle_technologies.caf.io.IOToolKit;
import com.circle_technologies.caf.logging.Log;
import com.circle_technologies.rnn.naive.context.NaiveNetworkContext;
import com.circle_technologies.rnn.naive.network.norm.SimpleNetworkNorm;
import com.google.gson.Gson;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.io.File;

/**
 * Created by Sellm on 24.08.2016.
 */
public class CommandSave extends AbstractNRNNCommand {

    public CommandSave(NaiveNetworkContext context) {
        super(context);
    }

    @Override
    public Options createOptions() {
        Options options = new Options();
        options.addOption(Option.builder("f").longOpt("file").hasArg().required().desc("" +
                "The folder path to store the network in").build());

        options.addOption(Option.builder("u").longOpt("update").hasArg(false).desc(
                "Flag indicating that a previously stored network is allowed to be overwritten"
        ).build());
        return options;
    }

    @Override
    public String getCommand() {
        return "save";
    }

    @Override
    public String execute(@Nullable CommandLine commandLine) {
        try {
            SimpleNetworkNorm norm = SimpleNetworkNorm.from(getContext().getNetworkNorm().get());
            if (norm == null) {
                Log.error("save", "No network norm found. Train network before storing");
                return "Failed storing network";
            }

            String folderPath = commandLine.getOptionValue("f");
            boolean overWrite = commandLine.hasOption("u");

            File folder = new File(folderPath);
            if (!folder.exists()) {
                boolean created = folder.mkdirs();
                if (created) {
                    Log.info("save", "Created folder structure");
                }
            }

            File[] files = folder.listFiles();
            if (!folder.isDirectory() || files == null) {
                Log.error("save", "Invalid filepath. Not a folder? Failed creating folder?");
                return "Failed storing network";
            }

            if (files.length > 0 && !overWrite) {
                Log.error("save", "Directory not empty. Use -u flag to overwrite");
                return "Failed storing network";
            }

            File networkFile = new File(folder, "rnn.net");
            File normFile = new File(folder, "rnn.nor");

            if (!getContext().getNetwork().save(networkFile)) {
                return "Failed storing network";
            }


            Gson gson = new Gson();
            String json = gson.toJson(norm);

            IOToolKit.writeStringToFile(normFile, json, false);

            return "Stored network in dir: " + folderPath;


        } catch (Exception e) {
            return "Failed storing network: Exception";
        }


    }
}
