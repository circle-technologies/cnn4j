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
import java.io.IOException;

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

            //Fetching the current network norm used.
            SimpleNetworkNorm norm = SimpleNetworkNorm.from(getContext().getNetworkNorm().get());

            //if norm == null >> never trained the network --> Saving is nonsense.
            if (norm == null) {
                Log.error("save", "No network norm found. Train network before storing");
                return "Failed storing network";
            }

            //Fetch params from input line
            String folderPath = commandLine.getOptionValue("f");
            boolean overWrite = commandLine.hasOption("u");

            //Create 'File' instance of folder
            File folder = new File(folderPath);

            //Check if folder structure is present.
            if (!folder.exists()) {

                //make all folders necessary
                boolean created = folder.mkdirs();
                if (created) {
                    Log.info("save", "Created folder structure");
                }
            }

            //Get all files listed in the folder --> this is null if file is not a folder
            File[] files = folder.listFiles();

            //Check for any indication, that the specified file is not a folder
            if (!folder.isDirectory() || files == null) {
                Log.error("save", "Invalid filepath. Not a folder? Failed creating folder?");
                return "Failed storing network";
            }

            //Abort storing if the folder contains any files and the -u flag was not passed.
            if (files.length > 0 && !overWrite) {
                Log.error("save", "Directory not empty. Use -u flag to overwrite");
                return "Failed storing network";
            }

            //Specify the files to store to.
            File networkFile = new File(folder, "rnn.net");
            File normFile = new File(folder, "rnn.nor");

            //Store the network. Abort and print on failure.
            if (!getContext().getNetwork().save(networkFile)) {
                return "Failed storing network";
            }


            //Store the norm with gson as json.
            Gson gson = new Gson();
            String json = gson.toJson(norm);
            IOToolKit.writeStringToFile(normFile, json, false);

            return "Stored network in dir: " + folderPath;


        } catch (IOException e) {
            return "Failed storing network: IOException";
        }


    }
}
