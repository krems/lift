package ru.ovchinnikov;


import ru.ovchinnikov.lift.Controller;

import java.util.Scanner;

class CommandListener {
    private final int floorsNum;
    private final double floorHeight;
    private final double speed;
    private final int doorsOpenedTime;
    private Controller controller;

    CommandListener(final int floorsNum, final double floorHeight,
                    final double speed, final int doorsOpenedTime) {
        this.floorsNum = floorsNum;
        this.floorHeight = floorHeight;
        this.speed = speed;
        this.doorsOpenedTime = doorsOpenedTime;
    }

    public void start() {
        configureLift(floorsNum, floorHeight, speed, doorsOpenedTime);
        controller.start();
        System.out.println("Main is at the 1st floor now");
        startListeningUserCommands();
    }

    private void startListeningUserCommands() {
        Scanner sc = new Scanner(System.in);
        CommandInterpreter interpreter = new CommandInterpreter(controller);
        while (sc.hasNextLine()) {
            if (interpreter.interpret(sc.nextLine())) {
                break;
            }
        }
        controller.stop();
        System.out.println("Stopping");
    }

    private void configureLift(final int floorsNum, final double floorHeight,
                               final double speed, final int doorsOpenedTime) {
        long oneFloorPassTimeMillis = calculateOneFloorPassTimeMillis(floorHeight, speed);
        this.controller = new Controller(floorsNum, oneFloorPassTimeMillis, doorsOpenedTime);
    }

    long calculateOneFloorPassTimeMillis(final double floorHeight, final double speed) {
        double timeSeconds = floorHeight / speed;
        return Math.round(timeSeconds * 1000);
    }
}
