package org.ucd.beag.model;

public class InstructionRegister {
    int ir;

    public void set(int val) {
        ir = val;
    }

    public int opCode() {
        int res = (ir & 0xf000) >> 12;
        return res;
    }

    public int rd() {
        int res = (ir & 0x0700) >> 8;
        return res;
    }

    public int rs1() {
        int res = (ir & 0x0070) >> 4;
        return res;
    }

    public int rs2() {
        int res = (ir & 0x0007);
        return res;
    }

    public int imm8() {
        int res = (ir & 0x00ff);
        res = (res ^ 0x80) - 0x80;  // sign-extend 8-bit value to the entire int
        return res;
    }


}
