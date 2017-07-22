package ru.ovchinnikov.lift.algo;

import org.junit.Before;
import org.junit.Test;
import ru.ovchinnikov.lift.Controller;
import ru.ovchinnikov.lift.LiftEngine;

import static org.junit.Assert.*;


public class LiftStateMachineTest {
    private LiftStateMachine stateMachine;
    private LiftEngine engine;
    private Controller controller;

    private int closeDoors;
    private int openDoors;
    private int moveDown;
    private int moveUp;

    @Before
    public void setUp() {
        closeDoors = 0;
        openDoors = 0;
        moveDown = 0;
        moveUp = 0;
        stateMachine = new LiftStateMachine();
        engine = new LiftEngine(0, 0);
        controller = new Controller(20, stateMachine, engine, 1) {
            public void closeDoors() {
                closeDoors++;
            }

            public void openDoors() {
                openDoors++;
            }

            public void moveDown() {
                moveDown++;
            }

            public void moveUp() {
                moveUp++;
            }
        };
        stateMachine.setController(controller);
    }

    @Test
    public void calledAtFloor_shouldMoveCabinUp_whenCalledFromAbove() throws Exception {
        stateMachine.calledAtFloor(7);

        assertEquals(1, moveUp);
    }

    @Test
    public void calledAtFloor_shouldMoveCabinDown_whenCalledFromBelow() throws Exception {
        stateMachine.arrivedAt(5);
        stateMachine.calledAtFloor(1);

        assertEquals(1, moveDown);
    }

    @Test
    public void calledAtFloor_shouldBeMemorized() throws Exception {
        stateMachine.calledAtFloor(5);
        stateMachine.calledAtFloor(1);
        stateMachine.arrivedAt(5);
        stateMachine.doorsOpened();
        stateMachine.doorsClosed();

        assertEquals(1, moveDown);
    }

    @Test
    public void floorSelected_shouldOverrideButtonsState() throws Exception {
        stateMachine.calledAtFloor(7);
        stateMachine.arrivedAt(4);
        stateMachine.floorSelected(3);

        assertEquals(1, moveDown);
    }

    @Test
    public void floorSelected_shouldMoveCabinUp_whenCalledFromAbove() throws Exception {
        stateMachine.floorSelected(7);

        assertEquals(1, moveUp);
    }

    @Test
    public void floorSelected_shouldMoveCabinDown_whenCalledFromBelow() throws Exception {
        stateMachine.calledAtFloor(5);
        stateMachine.arrivedAt(5);
        stateMachine.floorSelected(1);

        assertEquals(1, moveDown);
    }
}