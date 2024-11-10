package org.ucd.beag.view;

import org.ucd.beag.model.CPU;
import org.ucd.beag.model.Memory;

import java.io.IOException;

public interface UI {

    public abstract void displayWelcome();

    public abstract void displayState(CPU cpu) throws IOException;

    public abstract void displayMemory(Memory mem, int start, int size) throws IOException;

    public abstract void displayHelp();

    public abstract Command getCommand() throws IOException;
}
