package org.ucd.beag.model;

public class Registers {
    int []r;

    Registers()  {
        this(new int[]{0,0,0,0,0,0,0,0});
    }

    Registers(int []initRegs) {
        r = initRegs;
        r[0] = 0;
    }

    public int get(int i) {
        return r[i];
    }

    public void set(int i, int val) {
        if ((i <= 7) && (i >= 1)) {
            r[i] = val & 0xffff;
        }
    }
}
