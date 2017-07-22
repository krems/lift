package ru.ovchinnikov;


import org.apache.commons.cli.*;

import java.io.OptionalDataException;
import java.io.PrintWriter;

public class Main {
    private static final int REACTOR_CAPACITY = 1;
    private static final String FOOTER = "In app use commands:\r\n" +
            "call at $floor -- to signal call lift button was pressed at $floor\r\n" +
            "select floor $floor -- to signal button for $floor floor was pressed inside cabin\r\n" +
            "exit -- to exit";
    static final int MIN_NUMBER_OF_FLOORS = 5;
    static final int MAX_NUMBER_OF_FLOORS = 20;

    public static void main(final String[] args) {
        new Main().parseArgs(args);
    }

    void parseArgs(final String[] args) {
        Options options = new Options();

        Option floors = Option.builder("f")
                .longOpt("floors")
                .hasArg()
                .desc("Number of floors from " + MIN_NUMBER_OF_FLOORS + " to " + MAX_NUMBER_OF_FLOORS)
                .required().build();
        options.addOption(floors);

        Option height = Option.builder("fh")
                .longOpt("floor-height")
                .hasArg()
                .desc("Height of single floor (meters)")
                .required().build();
        options.addOption(height);

        Option velocity = Option.builder("v")
                .longOpt("velocity")
                .hasArg()
                .desc("Speed of lift (meters per second)")
                .required().build();
        options.addOption(velocity);

        Option openedTime = Option.builder("ot")
                .longOpt("opened-time")
                .hasArg()
                .desc("Time doors are kept opened (seconds)")
                .required().build();
        options.addOption(openedTime);

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine line = parser.parse(options, args);
            validateArgsAndRun(floors, height, velocity, openedTime, line);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            printUsage(options);
        } catch (Exception e) {
            printUsage(options);
        }
    }

    private void validateArgsAndRun(final Option floors, final Option height, final Option velocity,
                                    final Option openedTime, final CommandLine line) {
        int floorsNum = intValue(line, floors);
        validateFloorsNum(floorsNum);
        double floorHeight = doubleValue(line, height);
        double speed = doubleValue(line, velocity);
        int doorsOpenedTime = intValue(line, openedTime);

        CommandListener cmdListener = buildCommandListener(floorsNum, floorHeight, speed, doorsOpenedTime);
        cmdListener.start();
    }

    private void validateFloorsNum(final int floorsNum) {
        if (floorsNum < MIN_NUMBER_OF_FLOORS || floorsNum > MAX_NUMBER_OF_FLOORS) {
            System.err.println("Invalid number of floors. Should be from " + MIN_NUMBER_OF_FLOORS + " to "
                    + MAX_NUMBER_OF_FLOORS + ", but was: " + floorsNum);
            throw new IllegalArgumentException("Invalid number of floors");
        }
    }

    private int intValue(final CommandLine line, final Option floors) {
        String optionValue = line.getOptionValue(floors.getOpt());
        try {
            return Integer.parseInt(optionValue);
        } catch (NumberFormatException e) {
            System.err.println(floors.getOpt() + " should be an integer value, but is " + optionValue);
            throw e;
        }
    }

    private double doubleValue(final CommandLine line, final Option floors) {
        String optionValue = line.getOptionValue(floors.getOpt());
        try {
            return Double.parseDouble(optionValue);
        } catch (NumberFormatException e) {
            System.err.println(floors.getOpt() + " should be a double value, but is " + optionValue);
            throw e;
        }
    }

    private void printUsage(final Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("lift", null, options, FOOTER, true);
    }

    protected CommandListener buildCommandListener(final int floorsNum, final double floorHeight,
                                                   final double speed, final int doorsOpenedTime) {
        return new CommandListener(floorsNum, floorHeight, speed, doorsOpenedTime, REACTOR_CAPACITY);
    }
}
