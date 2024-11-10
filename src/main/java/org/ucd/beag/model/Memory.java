package org.ucd.beag.model;

import java.io.IOException;

public interface Memory {
    public int read(int address) throws IOException;
    public void write(int address, int data);
}
