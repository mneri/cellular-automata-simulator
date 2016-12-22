package me.mneri.ca.cli;

import static me.mneri.ca.cli.Constants.*;

import me.mneri.ca.app.Application;
import org.apache.commons.cli.*;

import java.io.File;

public class CommandLineInterface {
    private String[] mArgs;
    private Options mOptions = new Options();

    CommandLineInterface(String[] args) {
        mArgs = args;
        init();
    }

    private String getJarName() {
        File file = new File(CommandLineInterface.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        return file.getName();
    }

    private void init() {
        mOptions.addOption(OPT_HELP, OPT_HELP_LONG, false, OPT_HELP_DESC);
    }

    public static void main(String[] args) {
        CommandLineInterface cli = new CommandLineInterface(args);
        cli.parse();
    }

    private void parse() {
        try {
            CommandLineParser cmdParser = new DefaultParser();
            CommandLine cmd = cmdParser.parse(mOptions, mArgs);

            if (cmd.hasOption(OPT_HELP)) {
                printHelp();
            } else {
                Application.instance().run();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(getJarName(), mOptions, true);
    }
}
