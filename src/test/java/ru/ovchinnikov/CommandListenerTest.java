package ru.ovchinnikov;

import org.junit.Test;

import static org.junit.Assert.*;


public class CommandListenerTest {
    private CommandListener commandListener = new CommandListener(0, 3, 2, 0, 1);

    @Test
    public void calculateOneFloorPassTimeMillis_shouldCorrectlyCalculateTime() {
        long timeMillis = commandListener.calculateOneFloorPassTimeMillis(3, 2);
        assertEquals(1500, timeMillis);
    }
}