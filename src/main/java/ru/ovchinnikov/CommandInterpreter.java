package ru.ovchinnikov;


import ru.ovchinnikov.lift.Controller;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class CommandInterpreter {
    static final String SELECT_FLOOR_CMD = "select floor";
    static final String CALL_AT_CMD = "call at";
    static final String EXIT_CMD = "exit";
    private static final Pattern selectPattern = Pattern.compile(SELECT_FLOOR_CMD + "\\s+?(\\d+).*");
    private static final Pattern callPattern = Pattern.compile(CALL_AT_CMD + "\\s+?(\\d+).*");
    private final Controller controller;

    CommandInterpreter(final Controller controller) {
        this.controller = controller;
    }

    public boolean interpret(final String cmd) {
        if (cmd == null || cmd.isEmpty()) {
            return false;
        }
        String command = cmd.trim();
        if (command.startsWith(SELECT_FLOOR_CMD)) {
            Optional<Integer> floorMonad = parseFloor(command, selectPattern);
            floorMonad.ifPresent(controller::insideSelectedFloor);
            return false;
        }
        if (command.startsWith(CALL_AT_CMD)) {
            Optional<Integer> floorMonad = parseFloor(command, callPattern);
            floorMonad.ifPresent(controller::callAtFloor);
            return false;
        }
        return EXIT_CMD.equals(command);
    }

    private Optional<Integer> parseFloor(final String command, final Pattern pattern) {
        try {
            Matcher matcher = pattern.matcher(command);
            if (matcher.matches()) {
                String floorS = matcher.group(1);
                return Optional.of(Integer.parseInt(floorS));
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.empty();
    }
}
