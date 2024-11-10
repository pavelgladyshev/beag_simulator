package org.ucd.beag.model;

import java.io.File;
import java.io.FileNotFoundException;

// Random Access Memory made of 16 bit words
public class RAM16 extends ROM16 {
    public RAM16(int size) {
        super(size);
    }

    public RAM16(int size, int []init) {
        super(size, init);
    }

    public RAM16(int size, File f) throws FileNotFoundException {
        super(size, f);
    }
    @Override
    public void write(int address, int value) {
        array[address % array.length] = value & 0xffff;
    }
}
