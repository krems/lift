package ru.ovchinnikov.lift;


import ru.ovchinnikov.lift.algo.LiftStateMachine;
import ru.ovchinnikov.reactor.Reactor;

// @ThreadSafe
public class Controller {
    // @ThreadSafe
    private final Reactor reactor;
    // @ThreadConfined
    private final LiftStateMachine stateMachine;
    // @ThreadConfined
    private final LiftEngine liftEngine;
    private final int floorsNum;

    public Controller(final int floorsNum, final LiftStateMachine stateMachine,
                      final LiftEngine liftEngine, final int reactorCapacity) {
        this.floorsNum = floorsNum;
        this.stateMachine = stateMachine;
        this.liftEngine = liftEngine;
        this.reactor = new Reactor(liftEngine::openDoors, reactorCapacity);
    }

    public void callAtFloor(final int floor) {
        if (floor < 1 || floor > floorsNum) {
            System.err.println("Invalid floor number " + floor);
            return;
        }
        reactor.submit(() -> {
            System.out.println("Called at " + floor);
            stateMachine.calledAtFloor(floor);
        });
    }

    public void insideSelectedFloor(final int floor) {
        if (floor < 1 || floor > floorsNum) {
            System.err.println("Invalid floor number " + floor);
            return;
        }
        reactor.submit(() -> {
            System.out.println("Inside pressed " + floor);
            stateMachine.floorSelected(floor);
        });
    }

    public void start() {
        reactor.start();
    }

    public void stop() {
        reactor.stop();
    }

    public void onDoorsClosed() {
        reactor.submit(() -> {
            System.out.println("Doors closed");
            reactor.submit(stateMachine::doorsClosed);
        });
    }

    public void onDoorsOpened() {
        reactor.submit(() -> {
            System.out.println("Doors opened");
            reactor.submit(stateMachine::doorsOpened);
        });
    }

    public void onArrivedAtFloor(final int floor) {
        reactor.submit(() -> {
            System.out.println("Current floor: " + floor);
            reactor.submit(() -> stateMachine.arrivedAt(floor));
        });
    }

    public void closeDoors() {
        liftEngine.closeDoors();
    }

    public void moveDown() {
        liftEngine.moveOneFloorDown();
    }

    public void moveUp() {
        liftEngine.moveOneFloorUp();
    }

    public void openDoors() {
        liftEngine.openDoors();
    }
}
