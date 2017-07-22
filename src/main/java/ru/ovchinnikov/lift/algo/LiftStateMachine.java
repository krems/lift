package ru.ovchinnikov.lift.algo;

import ru.ovchinnikov.lift.Controller;

// @NotThreadSafe
public class LiftStateMachine {
    private Controller controller;
    private final ButtonsState buttonsState;
    private CabinState cabinState = CabinState.IDLE;
    private int currentFloor = 1;
    private int targetFloor = 1;
    private boolean targetFloorOverridable = true;

    public LiftStateMachine() {
        this.buttonsState = new ButtonsState();
    }

    public void setController(final Controller controller) {
        this.controller = controller;
    }

    public void calledAtFloor(final int floor) {
        if (!openedAtFloor(floor)) {
            buttonsState.setButton(floor);
        }
        if (cabinState == CabinState.IDLE) {
            targetFloor = floor;
            sendCabinTo(floor);
        }
    }

    boolean openedAtFloor(final int floor) {
        return cabinState == CabinState.DOORS_OPENED && floor == currentFloor;
    }

    private void sendCabinTo(final int floor) {
        if (floor == currentFloor) {
            cabinState = CabinState.DOORS_OPENED;
            controller.openDoors();
        } else if (floor > currentFloor) {
            controller.moveUp();
            cabinState = CabinState.MOVING_UP;
        } else {
            controller.moveDown();
            cabinState = CabinState.MOVING_DOWN;
        }
    }

    public void floorSelected(final int floor) {
        switch (cabinState) {
            case IDLE:
                targetFloor = floor;
                targetFloorOverridable = false;
                sendCabinTo(targetFloor);
                break;
            case DOORS_OPENED:
                targetFloor = floor;
                targetFloorOverridable = false;
                break;
            case MOVING_UP:
                if (targetFloorOverridable || floor > currentFloor) {
                    targetFloor = floor;
                    targetFloorOverridable = false;
                }
                break;
            case MOVING_DOWN:
                if (targetFloorOverridable || floor < currentFloor) {
                    targetFloor = floor;
                    targetFloorOverridable = false;
                }
                break;
            default:
                throw new IllegalStateException("Unknown enum constant " + cabinState);
        }
    }

    public void doorsOpened() {
        buttonsState.clearButton(currentFloor);
        if (currentFloor == targetFloor) {
            targetFloorOverridable = true;
        }
        controller.closeDoors();
    }

    public void doorsClosed() {
        cabinState = CabinState.IDLE;
        selectTargetFloor();
        if (currentFloor != targetFloor) {
            sendCabinTo(targetFloor);
        }
    }

    private void selectTargetFloor() {
        if (currentFloor != targetFloor || buttonsState.isEmpty()) {
            return;
        }
        buttonsState.getClosest(currentFloor).ifPresent(x -> targetFloor = x);
    }

    public void arrivedAt(final int floor) {
        currentFloor = floor;
        if (targetFloorOverridable && cabinState == CabinState.MOVING_DOWN) {
            buttonsState.getClosestLower(floor).ifPresent(x -> targetFloor = x);
        }
        if (targetFloorOverridable && cabinState == CabinState.MOVING_UP) {
            buttonsState.getClosestUpper(floor).ifPresent(x -> targetFloor = x);
        }
        sendCabinTo(targetFloor);
    }
}
