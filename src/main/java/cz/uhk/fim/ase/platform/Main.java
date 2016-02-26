package cz.uhk.fim.ase.platform;

import cz.uhk.fim.ase.platform.core.Config;
import cz.uhk.fim.ase.platform.core.Platform;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            CommandLineParser parser = new DefaultParser();
            Options options = createCommandLineOptions();
            CommandLine line = parser.parse(options, args);

            if (line.hasOption("h")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("java -jar target/platform.jar", options);
                return;
            }

            Config config = new Config();

            // platform
            if (line.hasOption("pa")) {
                config.setPlatformAddress(line.getOptionValue("pa"));
            }
            if (line.hasOption("pp")) {
                config.setPlatformPort(line.getOptionValue("pp"));
            }
            logger.info("Using " + config.getPlatformAddress() + ":" + config.getPlatformPort() + " as platform address");

            // router
            if (line.hasOption("ra")) {
                config.setRouterAddress(line.getOptionValue("ra"));
            }
            if (line.hasOption("ri")) {
                config.setRouterIncomingPort(line.getOptionValue("ri"));
            }
            if (line.hasOption("ro")) {
                config.setRouterOutcomingPort(line.getOptionValue("ro"));
            }
            logger.info("Using " + config.getRouterAddress() + ":" + config.getRouterIncomingPort() + " for publishing");
            logger.info("Subscribing to " + config.getRouterAddress() + ":" + config.getRouterOutcomingPort());

            // threads
            if (line.hasOption("ti")) {
                try {
                    config.setIoThreads(Math.abs(Integer.parseInt(line.getOptionValue("ti"))));
                } catch (NumberFormatException e) {
                    logger.error("I/O threads count must be integer, using default value");
                }
            }
            logger.info("Using " + config.getIoThreads() + " threads for I/O");
            if (line.hasOption("tc")) {
                try {
                    config.setComputingThreads(Math.abs(Integer.parseInt(line.getOptionValue("tc"))));
                } catch (NumberFormatException e) {
                    logger.error("Computing threads count must be integer, using default value");
                }
            }
            logger.info("Using " + config.getComputingThreads() + " threads for computing");

            Platform platform = new Platform(config);
            platform.start();
        } catch (ParseException e) {
            logger.error("Command line parser failed", e);
        }
    }

    private static Options createCommandLineOptions() {
        Options options = new Options();
        options.addOption("h", "help", false, "Help");

        options.addOption(Option.builder("pa")
                .longOpt("platform-address")
                .hasArg()
                .desc("Platform address")
                .build());
        options.addOption(Option.builder("pp")
                .longOpt("platform-port")
                .hasArg()
                .desc("Platform port")
                .build());

        options.addOption(Option.builder("ra")
                .longOpt("router-address")
                .hasArg()
                .desc("Router address")
                .build());
        options.addOption(Option.builder("ri")
                .longOpt("router-incoming-port")
                .hasArg()
                .desc("Router port for incoming communication")
                .build());
        options.addOption(Option.builder("ro")
                .longOpt("router-outcoming-port")
                .hasArg()
                .desc("Router port for outcoming communication")
                .build());

        options.addOption(Option.builder("ti")
                .longOpt("threads-io")
                .hasArg()
                .desc("Count of threads for communication layer (ZeroMQ I/O threads)")
                .build());
        options.addOption(Option.builder("tc")
                .longOpt("threads-computing")
                .hasArg()
                .desc("Count of threads for computing")
                .build());

        return options;
    }
}
