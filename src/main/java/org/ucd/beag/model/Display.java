package org.ucd.beag.model;

import org.ucd.beag.model.Memory;

import java.io.OutputStream;

public abstract class Display implements Memory {
    public abstract void printChar(int c);
    @Override
    public int read(int address) {
        return 0;
    }

    @Override
    public void write(int address, int value) {
        printChar(value & 0x7F);
    }
}
