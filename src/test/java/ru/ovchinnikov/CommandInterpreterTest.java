package ru.ovchinnikov;

import org.junit.Before;
import org.junit.Test;
import ru.ovchinnikov.lift.Controller;

import static org.junit.Assert.*;


public class CommandInterpreterTest {
    private int calledAt = -1;
    private int selectedFloor = -1;

    private CommandInterpreter interpreter = new CommandInterpreter(
            new Controller(0, null, null, 1) {
                @Override
                public void callAtFloor(int floor) {
                    calledAt = floor;
                }

                @Override
                public void insideSelectedFloor(int floor) {
                    selectedFloor = floor;
                }
            });

    @Before
    public void setUp() {
        int calledAt = -1;
        int selectFloor = -1;
    }

    @Test
    public void testInterpret_shouldDoNothing_whenCommandUnrecognized() {
        boolean stop = interpreter.interpret("ABRAKADABRA");
        assertFalse(stop);
        assertEquals(-1, calledAt);
        assertEquals(-1, selectedFloor);
    }

    @Test
    public void testInterpret_shouldReturnTrue_whenExitCommandSupplied() {
        boolean stop = interpreter.interpret(CommandInterpreter.EXIT_CMD);
        assertTrue(stop);
        assertEquals(-1, calledAt);
        assertEquals(-1, selectedFloor);
    }

    @Test
    public void testInterpret_shouldTellController_whenCallAtCommandSupplied() {
        boolean stop = interpreter.interpret(CommandInterpreter.CALL_AT_CMD + " 12");
        assertFalse(stop);
        assertEquals(12, calledAt);
        assertEquals(-1, selectedFloor);
    }

    @Test
    public void testInterpret_shouldTellController_whenSelectFloorCommandSupplied() {
        boolean stop = interpreter.interpret(CommandInterpreter.SELECT_FLOOR_CMD + " 12");
        assertFalse(stop);
        assertEquals(-1, calledAt);
        assertEquals(12, selectedFloor);
    }

    @Test
    public void testInterpret_shouldTellController_ignoringGarbageAtTheEnd_whenCallAtCommandSupplied() {
        boolean stop = interpreter.interpret(CommandInterpreter.CALL_AT_CMD + "\t12.342asdasd\\");
        assertFalse(stop);
        assertEquals(12, calledAt);
        assertEquals(-1, selectedFloor);
    }

    @Test
    public void testInterpret_shouldTellController_ignoringGarbageAtTheEnd_whenSelectFloorCommandSupplied() {
        boolean stop = interpreter.interpret(CommandInterpreter.SELECT_FLOOR_CMD + "   \t 12sdsdvsdv.2*\\");
        assertFalse(stop);
        assertEquals(-1, calledAt);
        assertEquals(12, selectedFloor);
    }
}