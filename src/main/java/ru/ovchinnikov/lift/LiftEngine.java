package ru.ovchinnikov.lift;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// @ThreadSafe
public class LiftEngine {
    private final ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor(task -> {
        Thread thread = new Thread(task);
        thread.setName("LiftEngine");
        thread.setDaemon(true);
        return thread;
    });
    private final long oneFloorTime;
    private final int doorsOpenedTime;
    // @ThreadSafe
    private Controller controller;
    // @ThreadConfined
    private int currentFloor = 1;

    public LiftEngine(final long oneFloorPassTime, final int doorsOpenedTime) {
        this.oneFloorTime = oneFloorPassTime;
        this.doorsOpenedTime = doorsOpenedTime;
    }

    public void setController(final Controller controller) {
        this.controller = controller;
    }

    public void moveOneFloorDown() {
        timer.schedule(() -> {
            currentFloor--;
            controller.onArrivedAtFloor(currentFloor);
        }, oneFloorTime, TimeUnit.MILLISECONDS);
    }

    public void moveOneFloorUp() {
        timer.schedule(() -> {
            currentFloor++;
            controller.onArrivedAtFloor(currentFloor);
        }, oneFloorTime, TimeUnit.MILLISECONDS);
    }

    public void openDoors() {
        timer.schedule(controller::onDoorsOpened, 0, TimeUnit.MILLISECONDS);
    }

    public void closeDoors() {
        timer.schedule(controller::onDoorsClosed, doorsOpenedTime, TimeUnit.SECONDS);
    }
}
