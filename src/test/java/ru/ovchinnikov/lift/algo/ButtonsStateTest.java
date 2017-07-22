package ru.ovchinnikov.lift.algo;

import org.junit.Test;

import javax.swing.text.html.Option;
import java.util.Optional;

import static org.junit.Assert.*;

public class ButtonsStateTest {
    @Test
    public void setButton_shouldKeepTrackedSeveralButtons() throws Exception {
        ButtonsState buttonsState = new ButtonsState();
        buttonsState.setButton(1);
        buttonsState.setButton(2);
        buttonsState.setButton(3);
        assertFalse(buttonsState.isEmpty());

        buttonsState.clearButton(1);
        assertFalse(buttonsState.isEmpty());

        buttonsState.clearButton(2);
        assertFalse(buttonsState.isEmpty());
    }

    @Test
    public void clearButton_shouldRemoveButtonFromState() throws Exception {
        ButtonsState buttonsState = new ButtonsState();
        buttonsState.setButton(1);
        assertFalse(buttonsState.isEmpty());

        buttonsState.clearButton(1);

        assertTrue(buttonsState.isEmpty());
    }

    @Test
    public void getClosestLower_shouldReturnMaximumSetFloorBelow() throws Exception {
        ButtonsState buttonsState = new ButtonsState();
        buttonsState.setButton(1);
        buttonsState.setButton(4);
        buttonsState.setButton(6);

        Optional<Integer> closestLower = buttonsState.getClosestLower(5);

        assertEquals(4, (int) closestLower.get());
    }

    @Test
    public void getClosestLower_shouldReturnEqualFloor_whenPresent() throws Exception {
        ButtonsState buttonsState = new ButtonsState();
        buttonsState.setButton(1);
        buttonsState.setButton(4);
        buttonsState.setButton(5);
        buttonsState.setButton(6);

        Optional<Integer> closestLower = buttonsState.getClosestLower(5);

        assertEquals(5, (int) closestLower.get());
    }

    @Test
    public void getClosestLower_shouldReturnEmpty_whenNoSuchFloorPresent() throws Exception {
        ButtonsState buttonsState = new ButtonsState();
        buttonsState.setButton(6);

        Optional<Integer> closestLower = buttonsState.getClosestLower(5);

        assertFalse(closestLower.isPresent());
    }

    @Test
    public void getClosestUpper_shouldReturnMinimalSetFloorAbove() throws Exception {
        ButtonsState buttonsState = new ButtonsState();
        buttonsState.setButton(4);
        buttonsState.setButton(6);
        buttonsState.setButton(9);

        Optional<Integer> closestLower = buttonsState.getClosestUpper(5);

        assertEquals(6, (int) closestLower.get());
    }

    @Test
    public void getClosestUpper_shouldReturnEqualFloor_whenPresent() throws Exception {
        ButtonsState buttonsState = new ButtonsState();
        buttonsState.setButton(4);
        buttonsState.setButton(5);
        buttonsState.setButton(6);
        buttonsState.setButton(9);

        Optional<Integer> closestLower = buttonsState.getClosestUpper(5);

        assertEquals(5, (int) closestLower.get());
    }

    @Test
    public void getClosestUpper_shouldReturnEmpty_whenNoSuchFloorPresent() throws Exception {
        ButtonsState buttonsState = new ButtonsState();
        buttonsState.setButton(1);

        Optional<Integer> closestLower = buttonsState.getClosestUpper(5);

        assertFalse(closestLower.isPresent());
    }

    @Test
    public void getClosest() throws Exception {
        ButtonsState buttonsState = new ButtonsState();
        buttonsState.setButton(4);
        buttonsState.setButton(6);
        buttonsState.setButton(9);

        Optional<Integer> closestLower = buttonsState.getClosest(5);

        assertEquals(6, (int) closestLower.get());
    }

    @Test
    public void getClosest_shouldReturnEqualFloor_whenPresent() throws Exception {
        ButtonsState buttonsState = new ButtonsState();
        buttonsState.setButton(4);
        buttonsState.setButton(5);
        buttonsState.setButton(6);
        buttonsState.setButton(9);

        Optional<Integer> closestLower = buttonsState.getClosest(5);

        assertEquals(5, (int) closestLower.get());
    }

    @Test
    public void getClosest_shouldReturnEmpty_whenStateIsEmpty() throws Exception {
        ButtonsState buttonsState = new ButtonsState();

        assertFalse(buttonsState.getClosest(5).isPresent());
    }
}