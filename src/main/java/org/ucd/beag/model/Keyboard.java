package org.ucd.beag.model;

import org.ucd.beag.model.Memory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.security.Key;

public abstract class Keyboard implements Memory {

    public abstract int available();

    public abstract int readChar();

    @Override
    public int read(int address) throws IOException {
        if (address == 0) {
            return available();
        } else {
            if (available()>0) {
                return readChar() & 0xffff;
            } else {
                return 0;
            }
        }
    }

    @Override
    public void write(int address, int value) {}
}
