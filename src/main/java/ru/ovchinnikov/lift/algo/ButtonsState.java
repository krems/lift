package ru.ovchinnikov.lift.algo;


import java.util.Optional;
import java.util.TreeSet;

// @NotThreadSafe
class ButtonsState {
    private final TreeSet<Integer> setButtons = new TreeSet<>();

    public void setButton(final int floor) {
        setButtons.add(floor);
    }

    public void clearButton(final int floor) {
        setButtons.remove(floor);
    }

    public Optional<Integer> getClosestLower(final int floor) {
        if (setButtons.isEmpty()) {
            return Optional.empty();
        }
        Integer result = setButtons.floor(floor);
        return Optional.ofNullable(result);
    }

    public Optional<Integer> getClosestUpper(final int floor) {
        if (setButtons.isEmpty()) {
            return Optional.empty();
        }
        Integer result = setButtons.ceiling(floor);
        return Optional.ofNullable(result);
    }

    public Optional<Integer> getClosest(final int floor) {
        Optional<Integer> closestLower = getClosestLower(floor);
        Optional<Integer> closestUpper = getClosestUpper(floor);
        if (!closestLower.isPresent()) {
            return closestUpper;
        }
        if (!closestUpper.isPresent()) {
            return closestLower;
        }
        if (floor - closestLower.get() < closestUpper.get() - floor) {
            return closestLower;
        }
        return closestUpper;
    }

    public boolean isEmpty() {
        return setButtons.isEmpty();
    }
}
