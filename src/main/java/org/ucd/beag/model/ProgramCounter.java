package org.ucd.beag.model;

import javax.swing.*;

public class ProgramCounter {
    int pc;

    ProgramCounter() {
        this(0);
    }

    ProgramCounter(int init) {
        pc = init & 0xffff;
    }

    public int get() {
        return pc;
    }

    public void set(int val) {
        pc = val & 0xffff;
    }

    public void add1() {pc = (pc + 1)&0xffff; }

}
