package ru.ovchinnikov;


import ru.ovchinnikov.lift.Controller;
import ru.ovchinnikov.lift.LiftEngine;
import ru.ovchinnikov.lift.algo.LiftStateMachine;

import java.util.Scanner;

class CommandListener {
    private final int floorsNum;
    private final double floorHeight;
    private final double speed;
    private final int doorsOpenedTime;
    private final int reactorCapacity;
    private Controller controller;

    CommandListener(final int floorsNum, final double floorHeight,
                    final double speed, final int doorsOpenedTime, final int reactorCapacity) {
        this.floorsNum = floorsNum;
        this.floorHeight = floorHeight;
        this.speed = speed;
        this.doorsOpenedTime = doorsOpenedTime;
        this.reactorCapacity = reactorCapacity;
    }

    public void start() {
        configureLift(floorsNum, floorHeight, speed, doorsOpenedTime, reactorCapacity);
        controller.start();
        System.out.println(floorsNum + " floors configured");
        System.out.println("Cabin is at the 1st floor now");
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
                               final double speed, final int doorsOpenedTime, final int reactorCapacity) {
        long oneFloorPassTimeMillis = calculateOneFloorPassTimeMillis(floorHeight, speed);
        LiftStateMachine stateMachine = new LiftStateMachine();
        LiftEngine liftEngine = new LiftEngine(oneFloorPassTimeMillis, doorsOpenedTime);
        this.controller = new Controller(floorsNum, stateMachine, liftEngine, reactorCapacity);
        stateMachine.setController(controller);
        liftEngine.setController(controller);
    }

    long calculateOneFloorPassTimeMillis(final double floorHeight, final double speed) {
        double timeSeconds = floorHeight / speed;
        return Math.round(timeSeconds * 1000);
    }
}
