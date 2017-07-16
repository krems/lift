package ru.ovchinnikov.lift;


import ru.ovchinnikov.reactor.Reactor;

public class Controller {
//    private final Reactor reactor = new Reactor();
//    private final LiftEngine liftEngine = new LiftEngine(reactor, );

    public Controller(int floorsNum, long oneFloorPassTimeMillis, int doorsOpenedTime) {

    }


    public void callAtFloor(int floor) {
        System.out.println("Called at " + floor);
    }

    public void insideSelectedFloor(int floor) {
        System.out.println("Inside pressed " + floor);
    }

    public void liftArrivedAtFloor(int floor) {

    }

    public void liftOpenedDoors() {

    }

    public void liftClosedDoors() {

    }

    public void start() {
//        reactor.start();
    }

    public void stop() {

    }
}
