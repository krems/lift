package ru.ovchinnikov;


import ru.ovchinnikov.lift.Controller;

class CommandInterpreter {
    private final Controller controller;

    CommandInterpreter(Controller controller) {
        this.controller = controller;
    }

    public boolean interpret(String cmd) {
        return false;
    }
}
