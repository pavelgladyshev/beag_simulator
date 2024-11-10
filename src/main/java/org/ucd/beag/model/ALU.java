package org.ucd.beag.model;

public class ALU {
    //takes 16 least significant bits of the parameter and sign-extends it to the entire int
    public static int int16se(int x) { return ((x & 0xffff) ^ 0x8000) - 0x8000; }
    public static int add16(int x, int y) { return (int16se(x) + int16se(y)) & 0xffff;}
    public static int sub16(int x, int y) { return (int16se(x) - int16se(y)) & 0xffff;}
    public static int mul16(int x, int y) { return (int16se(x) * int16se(y)) & 0xffff;}
    public static int div16(int x, int y) { try { return (int16se(x) / int16se(y)) & 0xffff; } catch (ArithmeticException e) { return x; } } // matches behaviour of Logisim divider
    public static int rem16(int x, int y) { try { return (int16se(x) % int16se(y)) & 0xffff; } catch (ArithmeticException e) { return 0; } } // matches behaviour of Logisim divider
}
